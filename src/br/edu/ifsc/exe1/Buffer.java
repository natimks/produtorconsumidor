package br.edu.ifsc.exe1;

// Fig. 23.6: Buffer.java
// Buffer interface specifies methods called by Producer and Consumer.

public interface Buffer 
{
   public void set( int value ); // place int value into Buffer
   public int get(); // return int value from Buffer
} // end interface Buffer
