package mars.mips.SO.ProcessManager;

import java.util.List;

public class SemaforoList {
  private static List<Semaforo> elementos;
  
  public static void create(int valor, int adress){
    Semaforo semaforo = new Semaforo(valor, adress);
    elementos.add(semaforo);
  }

  public static Semaforo getByAdress(int adress) throws Exception{
    for(Semaforo semaforo : elementos){
        if(semaforo.getAdress() == adress)
            return semaforo;
    }

    throw new Exception("Nenhum semáforo encontrado com esse endereço");
  }
}
