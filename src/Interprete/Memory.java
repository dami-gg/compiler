package Interprete;

/** Clase que permite emula el uso de memoria RAM.
 * Permite leer y escribir en la memoria datos de 2 y de 4 bytes (enteros y reales).
 * Las primeras direcciones de la memoria se usan para memoria estatica (variables
 * globales) mientras que la parte final de la memoria se usa para la pila.
 *
 * El registro SP indica cual es la posicion actual de la pila y cada vez que se
 * mete algo en la misma este se va decrementando. Esto quiere decir que si se meten en
 * la pila dos valores el segundo quedara en una direccion de memoria menor que el primero.
 *
 * Esta clase es una version de ejemplo que podra ser modificada a conveniencia
 * del alumno. Concretamente falta el control de acceso fuera de los limites de memoria.
 *
 */

/**
 *
 * @author Dami
 */
public class Memory {

  private byte[] mem;
  private int sp;

  /**
   * Crea un bloque de memoria de 1 Kb
   */
  public Memory() {
	this(1024);     
  }

  /**
   * El parametro es el tamaño del bloque de memoria a simular
   */
  public Memory(int bytes) {

      mem = new byte[bytes];
      sp = bytes;
  }

  /** Guarda un entero de dos bytes (short) en la direccion de memoria que se le indique
   * y en la siguiente.
   */
  public void writeInt(int direccion, short valor) {

      if (direccion >= 0 && direccion + 1 < 1024) {

        mem[direccion + 0] = (byte) (valor >>> 8);
        mem[direccion + 1] = (byte) (valor >>> 0);
      }

      else 

          System.out.println ("\nError en el acceso a memoria: intento de acceso a una posición fuera del espacio de memoria disponible");
  }

  /** Metodo sobrecargado para mayor comodidad y evitar tener que poner el cast al segundo
   * parametro cada vez que se invoque al metodo.
   */
  public void writeInt(int direccion, int valor) {

      writeInt(direccion, (short) valor);
  }

  /** Lee un entero (dos bytes) a partir de la direccion de memoria que se indique
   * como parametro.
   */
  public short readInt(int direccion) {

      if (direccion >= 0 && direccion + 1 < 1024)
          
          return (short) ( (mem[direccion] << 8) | (mem[direccion + 1] & 0xFF));

      else {

          System.out.println ("\nError en el acceso a memoria: intento de acceso a una posición fuera del espacio de memoria disponible");

          return (short) -1;
      }
  }

  /** Guarda un real de 4 bytes (float) en la direccion de memoria que se le indique
   * y en las tres siguientes.
   */
  public void writeFloat(int direccion, float valor) {

      if (direccion >= 0 && direccion + 3 < 1024) {

        int bits = Float.floatToIntBits(valor);

        mem[direccion + 0] = (byte) (bits >>> 24);
        mem[direccion + 1] = (byte) (bits >>> 16);
        mem[direccion + 2] = (byte) (bits >>> 8);
        mem[direccion + 3] = (byte) (bits >>> 0);
      }

      else 

          System.out.println ("\nError en el acceso a memoria: intento de acceso a una posición fuera del espacio de memoria disponible");
  }

  /** Metodo sobrecargado para mayor comodidad y evitar tener que poner el cast al segundo
   * parametro cada vez que se invoque al metodo.
   */
  public void writeFloat(int direccion, double valor) {

      writeFloat(direccion, (float) valor);
  }

  /** Lee un real (cuatro bytes) a partir de la direccion de memoria que se indique
   * como parametro.
   */
  public float readFloat(int direccion) {

      if (direccion >= 0 && direccion + 3 < 1024) {

        int bits = ( (mem[direccion + 0] << 24)
                    | ( (mem[direccion + 1] & 0xFF) << 16)
                    | ( (mem[direccion + 2] & 0xFF) << 8)
                    | (mem[direccion + 3] & 0xFF));

        return Float.intBitsToFloat(bits);
      }

      else {

          System.out.println("\nError en el acceso a memoria: intento de acceso a una posición fuera del espacio de memoria disponible");

          return Float.parseFloat("-1");
      }
  }

  /**
   * Guarda un byte o char en la dirección de memoria que se le indique
   * @param direccion
   * @param valor
   */
  public void writeByte (int direccion, byte valor) {

      if (direccion >= 0 && direccion < 1024)
      
        mem[direccion] = valor;

      else

          System.out.println ("\nError en el acceso a memoria: intento de acceso a una posición fuera del espacio de memoria disponible");
  }

  /**
   * Metodo sobrecargado para mayor comodidad y evitar tener que poner el cast al segundo
   * parametro cada vez que se invoque al metodo
   * @param direccion
   * @param valor
   */
  public void writeByte (int direccion, int valor) {

      writeByte (direccion, (byte) valor);
  }

  /**
   * Lee un byte o char a partir de la direccion de memoria que se indique como parametro.
   * @param direccion
   * @return
   */
  public byte readByte (int direccion) {

      if (direccion >= 0 && direccion < 1024)
      
        return mem[direccion];

      else {

          System.out.println("\nError en el acceso a memoria: intento de acceso a una posición fuera del espacio de memoria disponible");

          return (byte) -1;
      }
  }

  // Metodos auxiliares para trabajar con la pila (parte final de la memoria que
  // va creciendo hacia abajo

  public int getSP() {

      return sp;
  }

  public void setSP(int newSP) {

      sp = newSP;
  }

  public void pushInt(int valor) {

      sp -= 2;
	  writeInt(sp, valor);
  }

  public void pushFloat(double valor) {

      sp -= 4;
	  writeFloat(sp, valor);
  }

  public short popInt() {

      short valor = readInt(sp);
	  sp += 2;
	  return valor;
  }

  public float popFloat() {

      float valor = readFloat(sp);
	  sp += 4;
	  return valor;
  }

  public void pushByte (byte valor) {
      
      sp--;
      writeByte (sp, valor);
  }

  public byte popByte() {

      byte valor = readByte(sp);
      sp++;
      return valor;
  }
}