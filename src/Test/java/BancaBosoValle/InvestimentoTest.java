import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvestimentoTest {

  private Investimento investimento;

  @BeforeEach
  public void setUp() {
    // Creiamo un nuovo investimento con 1000€ di importo, durata "Medio", rischio "Alto", e 6 mesi
    // rimanenti
    investimento = new Investimento(1000.0, "Medio", "Alto", 6);
  }

  @Test
  public void testCreazioneInvestimento() {
    assertNotNull(investimento, "L'investimento dovrebbe essere correttamente creato.");
    assertEquals(
        1000.0, investimento.getImporto(), "L'importo dell'investimento dovrebbe essere 1000€.");
    assertEquals(
        "Medio", investimento.getDurata(), "La durata dell'investimento dovrebbe essere 'Medio'.");
    assertEquals(
        "Alto", investimento.getRischio(), "Il rischio dell'investimento dovrebbe essere 'Alto'.");
  }

  @Test
  public void testAggiornaInvestimento() {
    // Verifica che i mesi rimanenti siano 6 inizialmente
    assertEquals(6, investimento.getMesiRimanenti(), "I mesi rimanenti dovrebbero essere 6.");

    // Aggiorniamo l'investimento (simuliamo il passare di un mese)
    investimento.aggiornaInvestimento();
    assertEquals(
        5,
        investimento.getMesiRimanenti(),
        "I mesi rimanenti dovrebbero essere 5 dopo un aggiornamento.");

    // Aggiorniamo ulteriormente e vediamo se l'investimento diventa completato
    for (int i = 0; i < 5; i++) {
      investimento.aggiornaInvestimento();
    }

    assertTrue(investimento.completato(), "L'investimento dovrebbe essere completato dopo 6 mesi.");
  }

  @Test
  public void testCalcolaGuadagno() {
    // Verifica che il guadagno non venga calcolato prima che l'investimento sia completato
    assertEquals(
        0.0,
        investimento.calcolaGuadagno(),
        "Il guadagno dovrebbe essere 0 se l'investimento non è completato.");

    // Aggiorniamo l'investimento finché non è completato
    for (int i = 0; i < 6; i++) {
      investimento.aggiornaInvestimento();
    }

    // Verifica che il guadagno venga calcolato dopo che l'investimento è completato
    double guadagno = investimento.calcolaGuadagno();
    assertTrue(
        guadagno > 0, "Il guadagno dovrebbe essere positivo dopo che l'investimento è completato.");
  }

  @Test
  public void testToString() {
    // Verifica che la rappresentazione in stringa dell'investimento sia corretta
    String expectedString = "Investimento di 1000.0€, mesi rimanenti: 6, tasso di guadagno: ";
    assertTrue(
        investimento.toString().startsWith(expectedString),
        "Il metodo toString dovrebbe restituire una stringa corretta.");
  }
}
