package activity;

import java.util.*;

public class CalculadoraComision {
	private Scanner lector;
	private List< Integer > camarasVendidas;
	private List< Integer > mangosVendidos;
	private List< Integer > barrilesVendidos;
	private int limiteCamara = 70;
	private int limiteMango = 80;
	private int limiteBarril = 90;
	private boolean mesTerminado = false;
	
	public CalculadoraComision()
	{
		lector = new Scanner( System.in );
		camarasVendidas = new ArrayList< Integer >();
		mangosVendidos = new ArrayList< Integer >();
		barrilesVendidos = new ArrayList< Integer >();
	}
	
	private enum tipoResta
	{
		camara,
		mango,
		barril
	}
	
	private void leerDatos( Scanner lector, List< Integer > contenedor, int limite, tipoResta tipo )
	{
		if( lector.hasNextInt() )
			agregarAContenedor( lector, contenedor, limite, tipo );
		else
			terminarMes( lector.nextLine() );
		lector.nextLine();
	}
	
	private void agregarAContenedor( Scanner lector, List< Integer > contenedor, int limite, tipoResta tipo )
	{
		int temp = lector.nextInt();
		if( temp > -1 ) {
			if( temp <= limite ) {
				restarLimites( temp, tipo );
				contenedor.add( temp );
			}
			else
				System.out.println( "No hay suficiente inventario para la demanda." );
		}
		else
			System.out.println( "El numero introducido no es valido." );
	}
	
	private void restarLimites( int cantidadARestar, tipoResta tipo )
	{
		switch( tipo.ordinal() )
		{
			case 0:
				limiteCamara -= cantidadARestar;
				break;
			case 1:
				limiteMango -= cantidadARestar;
				break;
			case 2:
				limiteBarril -= cantidadARestar;
				break;
		}
	}
	
	private void terminarMes( String datosIntroducidos )
	{
		if( datosIntroducidos.equals( "Fin de Mes" ) )
			mesTerminado = true;
		else
			System.out.println( "Los datos introducidos no son validos." );
	}
	
	private double getComision()
	{
		int gananciaTotal = 0;
		gananciaTotal += getGananciaTotal( camarasVendidas, 45 );
		gananciaTotal += getGananciaTotal( mangosVendidos, 30 );
		gananciaTotal += getGananciaTotal( barrilesVendidos, 20 );
		return calcularComision( gananciaTotal );
	}
	
	public double calcularComision( int gananciaTotal )
	{
		double temp = 0.0;
		if( gananciaTotal > 0  && gananciaTotal <= 1000 )
			temp = gananciaTotal * 0.1;
		else if( gananciaTotal > 1000 && gananciaTotal <= 1800 )
			temp = gananciaTotal * 0.18;
		else if( gananciaTotal > 1800 )
			temp = gananciaTotal * 0.2;
		return temp;
	}
	
	private int getCantidadVendida( List< Integer > contenedor )
	{
		int temp = 0;
		for( int cantidad: contenedor )
			temp += cantidad;
		return temp;
	}
	
	private int getGananciaTotal( List< Integer > contenedor, int valorPorUnidad )
	{
		return getCantidadVendida( contenedor ) * valorPorUnidad;
	}
	
	private boolean ganoComision( int cantidadCamaras, int cantidadMangos, int cantidadBarriles )
	{
		return ( cantidadCamaras > 0 && cantidadMangos > 0 && cantidadBarriles > 0 );
	}
	
	private void resultados()
	{
		if( ganoComision( getCantidadVendida( camarasVendidas ), getCantidadVendida( mangosVendidos ), 
			getCantidadVendida( barrilesVendidos ) ) ) {
			darComision( getComision() );
		}
		else
			System.out.println( "Estas a prueba." );
	}
	
	private void darComision( double comision)
	{
		if( comision < 100.0 ) {
			System.out.println( "Tu comision es de:" + comision );
			System.out.println( "Debes esforzarte.");
		}
		
		if( comision >= 100.0 && comision < 112.0 ) {
			System.out.println( "Tu comision es de:" + comision );
			System.out.println( "Sigue asi.");
		}
		
		if( comision >= 112.0 && comision <= 1000.0 ) {
			System.out.println( "Tu comision es de:" + comision );
			System.out.println( "Felicidades");
		}
		
		if( comision > 1000.0 ) {
			System.out.println( "Tu comision es de:" + comision );
			System.out.println( "Vendedor estrella.");
		}
	}
	
	public void run()
	{
		while( !mesTerminado )
		{
			if( !mesTerminado ) {
			System.out.println( "Si ya terminaste tu mes introduce: Fin de Mes" );
			System.out.println( "Introduce la cantidad de camaras vendidas." );
			leerDatos( lector, camarasVendidas, limiteCamara, tipoResta.camara );
			}
			
			if( !mesTerminado ) {
			System.out.println( "Introduce la cantidad de mangos vendidos." );
			leerDatos( lector, mangosVendidos, limiteMango, tipoResta.mango );
			}
			
			if( !mesTerminado ) {
			System.out.println( "Introduce la cantidad de barriles vendidos" );
			leerDatos( lector, barrilesVendidos, limiteBarril, tipoResta.barril );
			}
		}
		resultados();
	}
}