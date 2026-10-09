package presentation;
import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Ventana principal del EcoSafari.
 * Muestra el tablero con las entidades (animales, plantas, etc.)
 * y un botón para avanzar el tiempo (tic-tac).
 *
 * @author MoralesS-RojasH
 */
public class EcoSafariGUI extends JFrame{  
    public static final int SIDE=20;

    public final int SIZE;
    private JButton ticTacButton;
    private JPanel  controlPanel;
    private PhotoEcoSafari photo;
    private EcoSafari theEcoSafari;
   
    /**
     * Crea la ventana y arma el EcoSafari que se va a mostrar.
     */
    private EcoSafariGUI() {
        theEcoSafari=new EcoSafari();
        SIZE=theEcoSafari.getSize();
        prepareElements();
        prepareActions();
    }
    
    /**
     * Arma los elementos visuales de la ventana (foto y botón).
     */    
    private void prepareElements() {
        setTitle("EcoSafari");
        photo=new PhotoEcoSafari(this);
        ticTacButton=new JButton("Tic-tac");
        setLayout(new BorderLayout());
        add(photo,BorderLayout.NORTH);
        add(ticTacButton,BorderLayout.SOUTH);
        setSize(new Dimension(SIDE*SIZE+15,SIDE*SIZE+72)); 
        setResizable(false);
        photo.repaint();
    }
    
    /**
     * Configura las acciones de la ventana, como cerrar y el botón de tic-tac.
     */
    private void prepareActions(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);       
        ticTacButton.addActionListener(e-> ticTacButtonAction());
    }
    
    /**
     * Avanza un tic-tac en el EcoSafari y actualiza el dibujo.
     */
    private void ticTacButtonAction() {
        theEcoSafari.ticTac();
        photo.repaint();
    }

    /**
     * Devuelve el EcoSafari que está manejando esta ventana.
     *
     * @return el EcoSafari actual
     */    
    public EcoSafari gettheEcoSafari(){
        return theEcoSafari;
    }
    
    /**
     * Punto de entrada del programa. Crea la ventana y la muestra.
     *
     * @param args argumentos de consola (no se usan)
     */    
    public static void main(String[] args) {
        EcoSafariGUI cg=new EcoSafariGUI();
        cg.setVisible(true);
    }  

    /**
     * Panel donde se dibuja el tablero del EcoSafari con todas sus entidades.
     */
    class PhotoEcoSafari extends JPanel{
        private EcoSafariGUI gui;
        
        /**
         * Crea el panel de dibujo asociado a la ventana principal.
         *
         * @param gui la ventana principal que contiene este panel
         */    
        public PhotoEcoSafari(EcoSafariGUI gui) {
            this.gui=gui;
            setBackground(Color.white);
            setPreferredSize(new Dimension(gui.SIDE*gui.SIZE+10, gui.SIDE*gui.SIZE+10));         
        }
    
        //Ayuda de IA generativa para suavizar el gris y el metodo painComponent
        
        /**
         * Dibuja el tablero: la cuadrícula y cada entidad con su color y forma.
         *
         * @param g el contexto gráfico donde se dibuja
         */        
        public void paintComponent(Graphics g){
            EcoSafari theEcoSafari=gui.gettheEcoSafari();
            super.paintComponent(g);
             
            for (int c=0;c<=theEcoSafari.getSize();c++){
                g.drawLine(c*gui.SIDE,0,c*gui.SIDE,theEcoSafari.getSize()*gui.SIDE);
            }
            for (int f=0;f<=theEcoSafari.getSize();f++){
                g.drawLine(0,f*gui.SIDE,theEcoSafari.getSize()*gui.SIDE,f*gui.SIDE);
            }       
            for (int f=0; f<theEcoSafari.getSize(); f++){
                for (int c=0; c<theEcoSafari.getSize(); c++){
            
                    if (theEcoSafari.get(f,c) != null){
                        Color entityColor = theEcoSafari.get(f,c).getColor();
                        boolean esZonaTierra = (theEcoSafari.get(f,c) instanceof Soil)
                            || (theEcoSafari.get(f,c) instanceof Grass)
                            || (theEcoSafari.get(f,c) instanceof Zebra)
                            || (theEcoSafari.get(f,c) instanceof Lion);
                        if (theEcoSafari.isAffected(f,c) && !esZonaTierra){
                            entityColor = entityColor.darker();
                        }
                        g.setColor(entityColor);
                        if (theEcoSafari.get(f,c).shape()==Entity.SQUARE){
                            g.fillRoundRect(gui.SIDE*c+1, gui.SIDE*f+1, gui.SIDE-2, gui.SIDE-2, 2, 2);
                        } else {
                            g.fillOval(gui.SIDE*c+1, gui.SIDE*f+1, gui.SIDE-2, gui.SIDE-2);
                        }
                        if (theEcoSafari.get(f,c).isOrganism()){
                            g.setColor(Color.red);
                            if (((Organism)theEcoSafari.get(f,c)).getEnergy()>=50){
                                g.drawString("+", gui.SIDE*c+6, gui.SIDE*f+15);
                            } else {
                                g.drawString("~", gui.SIDE*c+6, gui.SIDE*f+17);
                            }
                        }
                    } else if (theEcoSafari.isAffected(f,c)){
                        g.setColor(Color.GRAY);
                        g.fillRect(gui.SIDE*c+1, gui.SIDE*f+1, gui.SIDE-2, gui.SIDE-2);
                    }
                }
            }
        }
        
    }
}