package ticketmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
		// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
		// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
		// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
		// S3: on n’imprime pas leticket si le montant inséré est insuffisant
	void notPrintIfMoneyIsInsufficient(){
		machine.insertMoney(PRICE-1);
		assertFalse(machine.printTicket(), "Le ticket ne doit pas pouvoir être imprimer");
	}

	@Test
		// S4: on imprime le ticket si le montant inséré est suffisant
	void printIfMoneyIsSufficient(){
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "Le ticket doit s'imprimer");
	}

	@Test
		// S5: Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void decreaseBalanceWhenPrintTicket(){
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(machine.getBalance(), 0, "La balance doit être mise à jour lors de l'impression d'un ticket");
	}

	@Test
		// S6: le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void collectMoneyWhenTicketIsPrint(){
		machine.insertMoney(PRICE);
		assertEquals(machine.getTotal(), 0, "Le total doit être mise à jour lors de l'impression d'un ticket");
		machine.printTicket();
		assertEquals(machine.getTotal(), PRICE, "Le total doit être mise à jour lors de l'impression d'un ticket");
	}

	@Test
		// S7 : refund()rendcorrectement la monnaie
	void refundMoney(){
		machine.insertMoney(20);
		assertEquals(machine.refund(), 20, "La machine ne rend pas correctement la monnaie");
	}

	@Test
		// S8 : refund()remet la balance à zéro
	void refundMoneySetBalanceToZero(){
		machine.insertMoney(20);
		machine.refund();
		assertEquals(machine.getBalance(), 0, "La machine ne réinitialise pas la balance correctement");
	}

	@Test
		// S9 : on ne peut pas insérer un montant négatif
	void cantInsertNegativeMoney(){
		assertThrows(IllegalArgumentException.class, () -> {machine.insertMoney(-1);},  "La machine ne doit pas recevoir des montants négatifs");
	}

	@Test
		// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void cantCreateTicketMachineWithNegativePrice(){
		assertThrows(IllegalArgumentException.class, () -> {new TicketMachine(-1);}, "");
	}
}
