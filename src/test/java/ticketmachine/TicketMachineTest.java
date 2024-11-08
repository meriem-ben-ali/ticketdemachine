package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
		// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
		// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
		// S3 : N'imprime pas si pas assez d'argent
	void nImprimePasSiBalanceInsuffisante() {
		machine.insertMoney(PRICE - 1);
		assertFalse(machine.printTicket(), "Pas assez d'argent, on ne doit pas imprimer");
	}

	@Test
		// S4 : Imprime si assez d'argent
	void imprimeSiBalanceSuffisante() {
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "Il y a assez d'argent, on doit imprimer");
	}

	@Test
		// S5 : La balance est réinitialisée après l'impression
	void balanceIsResetAfterPrinting() {
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(0, machine.getBalance(), "La balance devrait être réinitialisée à 0 après impression");
	}

	@Test
		// S6 : L'argent excédentaire doit être retourné
	void remainingMoneyIsReturnedAfterPrinting() {
		machine.insertMoney(70); // On insère 70 centimes
		boolean printed = machine.printTicket();
		assertTrue(printed, "Le ticket devrait être imprimé");
		assertEquals(0, machine.getBalance(), "La balance devrait être réinitialisée à 0");
		assertEquals(20, machine.getTotal(), "Le total collecté devrait être 50");
	}

	@Test
		// S7 : On peut rembourser de l'argent
	void refundReturnsCorrectAmount() {
		machine.insertMoney(100);
		int refundedAmount = machine.refund();
		assertEquals(100, refundedAmount, "Le montant remboursé doit être égal à la balance");
		assertEquals(0, machine.getBalance(), "La balance doit être réinitialisée après remboursement");
	}

	@Test
		// S8 : On peut changer le prix du ticket
	void canChangePrice() {
		machine.setPrice(60); // Changer le prix à 60 centimes
		assertEquals(60, machine.getPrice(), "Le prix du ticket doit être mis à jour");
	}
}
