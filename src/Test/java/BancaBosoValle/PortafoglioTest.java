import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PortafoglioTest {

  private Portafoglio portafoglio;

  @BeforeEach
  public void setUp() {
    // Creiamo un nuovo portafoglio con saldo iniziale di 1000.0
    portafoglio = new Portafoglio(1000.0);
  }

  @Test
  public void testSaldoIniziale() {
    // Verifica che il saldo iniziale sia correttamente impostato a 1000.0
    assertEquals(1000.0, portafoglio.getSaldo(), "Il saldo iniziale dovrebbe essere 1000.0");
  }

  @Test
  public void testAggiungiDenaro() {
    // Aggiungiamo 500.0 al saldo e verifichiamo che il saldo sia corretto
    portafoglio.aggiungiDenaro(500.0);
    assertEquals(
        1500.0,
        portafoglio.getSaldo(),
        "Il saldo dopo l'aggiunta di denaro dovrebbe essere 1500.0");
  }

  @Test
  public void testPrelevaDenaroConSaldoSufficiente() {
    // Preleviamo 400.0 e verifichiamo che il saldo venga aggiornato correttamente
    boolean successo = portafoglio.prelevaDenaro(400.0);
    assertTrue(successo, "Il prelievo dovrebbe riuscire con saldo sufficiente.");
    assertEquals(600.0, portafoglio.getSaldo(), "Il saldo dopo il prelievo dovrebbe essere 600.0");
  }

  @Test
  public void testPrelevaDenaroConSaldoInsufficiente() {
    // Cerchiamo di prelevare più di quanto disponibile
    boolean successo = portafoglio.prelevaDenaro(1500.0);
    assertFalse(successo, "Il prelievo dovrebbe fallire se il saldo è insufficiente.");
    assertEquals(
        1000.0, portafoglio.getSaldo(), "Il saldo non dovrebbe cambiare dopo un prelievo fallito.");
  }

  @Test
  public void testToString() {
    // Verifica che la rappresentazione in stringa del portafoglio sia corretta
    assertEquals(
        "1000.0",
        portafoglio.tostring(),
        "Il metodo toString dovrebbe restituire il saldo corretto come stringa.");
  }
}
