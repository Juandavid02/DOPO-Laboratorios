package presentation;
import domain.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter; //Usar el filtro 
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
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newItem;
    private JMenuItem openItem;
    private JMenuItem saveAsItem;
    private JMenuItem exitItem;
    private JMenuItem importItem;
    private JMenuItem exportAsItem;
    /**
     * Crea la ventana y arma el EcoSafari que se va a mostrar.
     */
    private EcoSafariGUI() {
        theEcoSafari=new EcoSafari();
        SIZE=theEcoSafari.getSize();
        prepareElements();
        prepareViewMenu();
        prepareActions();
    }


    /**
     * Arma el menú Archivo con sus seis opciones y los separadores.
     */
    private void prepareViewMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Archivo");
        newItem = new JMenuItem("Nuevo");
        openItem = new JMenuItem("Abrir");
        saveAsItem = new JMenuItem("Salvar como");
        importItem = new JMenuItem("Importar");
        exportAsItem = new JMenuItem("Exportar como");
        exitItem = new JMenuItem("Salir");

        fileMenu.add(newItem);
        fileMenu.addSeparator();
        fileMenu.add(openItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(importItem);
        fileMenu.add(exportAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
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

        ticTacButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticTacButtonAction();
            }
        });

        prepareControllerMenu();
    }
    
    /**
     * Conecta cada opción del menú Archivo con su método.
     */
    private void prepareControllerMenu(){
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionNew();
            }
        });

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionOpen();
            }
        });

        saveAsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionSaveAs();
            }
        });

        importItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionImport();
            }
        });

        exportAsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionExportAs();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionExit();
            }
        });
    }

    /**
     * Crea un safari nuevo y lo dibuja.
     */
    private void optionNew() {
        theEcoSafari = new EcoSafari();
        photo.repaint();
    }

    /**
     * Cierra la aplicación.
     */
    private void optionExit() {
        System.exit(0);
    }

    /**
     * Pide un archivo y llama a abrir del modelo.
     */
    private void optionOpen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos safari (*.dat)", "dat")); //Recomendacion de IA para que solo se muestren archivos .dat
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                theEcoSafari = theEcoSafari.open(chooser.getSelectedFile());
                photo.repaint();
            } catch (EcoSafariException e) {
                showMessage(e.getMessage());
            }
        }
    }

    /**
     * Pide un archivo y llama a salvar como del modelo.
     */
    private void optionSaveAs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos safari (*.dat)", "dat")); //Recomendacion de IA para que solo se muestren archivos .dat
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                theEcoSafari.saveAs(chooser.getSelectedFile());
            } catch (EcoSafariException e) {
                showMessage(e.getMessage());
            }
        }
    }

    /**
     * Pide un archivo y llama a importar del modelo.
     */
    private void optionImport() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                theEcoSafari.import_(chooser.getSelectedFile());
                photo.repaint();
            } catch (EcoSafariException e) {
                showMessage(e.getMessage());
            }
        }
    }

    /**
     * Pide un archivo y llama a exportar como del modelo.
     */
    private void optionExportAs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                theEcoSafari.exportAs(chooser.getSelectedFile());
            } catch (EcoSafariException e) {
                showMessage(e.getMessage());
            }
        }
    }

    /**
     * Muestra un mensaje en una ventanita.
     *
     * @param message el texto a mostrar
     */
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "EcoSafari", JOptionPane.INFORMATION_MESSAGE);
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