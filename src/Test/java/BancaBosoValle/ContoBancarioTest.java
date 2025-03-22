import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContoBancarioTest {

  private ContoBancario conto;

  @BeforeEach
  public void setUp() {
    // Creiamo un nuovo ContoBancario con un saldo iniziale di 100.0
    conto = new ContoBancario(100.0);
  }

  @Test
  public void testSaldoIniziale() {
    // Verifica che il saldo iniziale sia correttamente impostato a 100.0
    assertEquals(100.0, conto.getSaldo(), "Il saldo iniziale dovrebbe essere 100.0");
  }

  @Test
  public void testDeposita() {
    // Depositiamo 50.0 e verifichiamo che il saldo sia aggiornato correttamente
    conto.deposita(50.0);
    assertEquals(150.0, conto.getSaldo(), "Il saldo dopo il deposito dovrebbe essere 150.0");
  }

  @Test
  public void testPrelevaConSaldoSufficiente() {
    // Preleviamo 50.0 e verifichiamo che il saldo sia aggiornato correttamente
    boolean successo = conto.preleva(50.0);
    assertTrue(successo, "Il prelievo dovrebbe riuscire con saldo sufficiente.");
    assertEquals(50.0, conto.getSaldo(), "Il saldo dopo il prelievo dovrebbe essere 50.0");
  }

  @Test
  public void testPrelevaConSaldoInsufficiente() {
    // Cerchiamo di prelevare più di quanto disponibile
    boolean successo = conto.preleva(200.0);
    assertFalse(successo, "Il prelievo dovrebbe fallire se il saldo è insufficiente.");
    assertEquals(
        100.0, conto.getSaldo(), "Il saldo non dovrebbe cambiare dopo un prelievo fallito.");
  }

  @Test
  public void testToString() {
    // Verifica che il metodo toString restituisca il saldo come stringa
    assertEquals(
        "100.0",
        conto.tostring(),
        "Il metodo toString dovrebbe restituire il saldo corretto come stringa.");
  }
}
