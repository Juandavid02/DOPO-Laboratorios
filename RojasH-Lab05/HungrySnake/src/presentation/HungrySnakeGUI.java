package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import domain.*;

/**
 * Ventana principal del juego HungrySnake (capa de presentación).
 * Muestra una pantalla de inicio con el logo y el tablero de juego, con un
 * menú para cambiar colores, la configuración y reiniciar la partida.
 * Usa el esquema prepareView - prepareController.
 *
 * @author  Juan David Rojas Heredia
 * @version 28-10-2026
 */
public class HungrySnakeGUI extends JFrame {
    
    private JMenuItem itemNuevo; 
    private JMenuItem itemAbrir; 
    private JMenuItem itemSalir;
    private JMenuItem itemSalvar;
    private JMenuItem itemColorSerpiente;
    private JMenuItem itemColorFrutas;
    private JMenuItem itemColorObstaculos;
    private JMenuItem itemConfiguracion;

    private JPanel panelGrid;
    private JLabel tamano;
    private JPanel[][] celdas;

    private HungrySnake juego;
    private Color colorSerpiente = Color.BLUE;
    private Color colorFrutas = Color.RED;
    private Color colorObstaculos = Color.BLACK;

    private CardLayout cartas;
    private JPanel contenedor;
    private Timer reloj;

    private JButton botonPlay;
    private JButton botonSalir;

    /**
     * Crea la ventana con un juego de 25x25, 1 fruta y 2% de obstáculos.
     * Si la configuración inicial no es válida, muestra el error y cierra.
     */
    public HungrySnakeGUI() {
        super("HungrySnake");
        try{
            juego = new HungrySnake(25, 25, 1, 2);
        } catch(HungrySnakeException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);       
        }
        prepareView();
        prepareController(); 
    }

    /**
     * Crea la ventana y la hace visible.
     *
     * @param args los argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        HungrySnakeGUI gui = new HungrySnakeGUI();
        gui.setVisible(true);
    }

    /**
     * Prepara la vista: tamaño de la ventana (la mitad de la pantalla),
     * posición en el centro, y los paneles del menú, el inicio y el tablero.
     */
    private void prepareView(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = screenSize.width / 2;
        int alto = screenSize.height / 2;
        setSize(ancho, alto);
        setLocationRelativeTo(null);
        cartas = new CardLayout();
        contenedor = new JPanel(cartas);
        this.add(contenedor);
        prepareViewMenu();
        prepareViewInicio();
        prepareViewBoard();
    }

    /**
     * Prepara la barra de menú con los menús Archivo y Opciones.
     */
    private void prepareViewMenu(){
        JMenuBar barraMenu = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenu menuOpciones = new JMenu("Opciones");
        itemNuevo = new JMenuItem("Nuevo");
        itemAbrir = new JMenuItem("Abrir");
        itemSalvar = new JMenuItem("Salvar");
        itemSalir = new JMenuItem("Salir");
        itemColorSerpiente = new JMenuItem("Color de la Serpiente");
        itemColorFrutas = new JMenuItem("Color de las frutas");
        itemColorObstaculos = new JMenuItem("Color de los Obstaculos");
        itemConfiguracion = new JMenuItem("Configuracion...");

        menuArchivo.add(itemNuevo);
        menuArchivo.addSeparator();
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemSalvar);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);
        menuOpciones.add(itemColorSerpiente);
        menuOpciones.add(itemColorFrutas);
        menuOpciones.add(itemColorObstaculos);
        menuOpciones.addSeparator();
        menuOpciones.add(itemConfiguracion);

        barraMenu.add(menuArchivo);
        barraMenu.add(menuOpciones);
        
        setJMenuBar(barraMenu);
    }

    /**
     * Prepara la pantalla de inicio: el logo y los botones Play y Salir.
     */
    private  void prepareViewInicio(){
        JPanel panelInicio = new JPanel(new BorderLayout());
        panelInicio.setBackground(new Color(34, 120, 50));
        ImageIcon iconoOriginal = new ImageIcon("resources/Logo.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon iconoRedimensionado = new ImageIcon(imagenEscalada);
        JLabel logo = new JLabel(iconoRedimensionado);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        panelInicio.add(logo, BorderLayout.CENTER);
        botonPlay = new JButton("Play");
        botonSalir = new JButton("Salir");
        botonPlay.setPreferredSize(new Dimension(150, 50));
        botonSalir.setPreferredSize(new Dimension(150, 50));
        botonPlay.setFont(new Font("Arial", Font.BOLD, 20));
        botonSalir.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.add(botonPlay);
        panelBotones.add(botonSalir);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0)); //IA generativa para poder subir 40 px los botones

        panelInicio.add(panelBotones, BorderLayout.SOUTH);

        contenedor.add(panelInicio, "inicio");
    } 

    /**
     * Prepara la pantalla del juego: la etiqueta con el tamaño de la
     * serpiente arriba y el tablero en el centro.
     */
    private void prepareViewBoard(){
        JPanel panelJuego = new JPanel(new BorderLayout());
        JPanel panelNorte = new JPanel();
        tamano = new JLabel("La serpiente tiene un tamaño de 1");
        panelNorte.add(tamano);
        panelJuego.add(panelNorte, BorderLayout.NORTH);

        panelGrid = new JPanel();
        panelJuego.add(panelGrid, BorderLayout.CENTER);
        construirTablero();
        contenedor.add(panelJuego, "juego");
        refresh();
    }

    /**
     * Construye la cuadrícula de celdas según las filas y columnas del juego.
     */
    private void construirTablero(){
        panelGrid.removeAll();
        panelGrid.setLayout(new GridLayout(juego.getFilas(), juego.getColumnas()));
        celdas = new JPanel[juego.getFilas()][juego.getColumnas()];
        for (int f = 0; f<juego.getFilas(); f++){
            for (int c = 0; c<juego.getColumnas(); c++){
                celdas[f][c] = crearCelda(f, c);
                panelGrid.add(celdas[f][c]);
            }
        }
        panelGrid.revalidate();
        panelGrid.repaint();
    }

    /**
     * Crea una celda del tablero con su color de fondo.
     *
     * @param f la fila de la celda
     * @param c la columna de la celda
     * @return el panel que representa la celda
     */
    private JPanel crearCelda(int f, int c) {
        JPanel celda = new JPanel();
        celda.setBackground(colorDeFondo(f, c));    
        return celda;
    }

    /**
     * Da el color de fondo de una celda vacía. Alterna verde oscuro y
     * verde claro como un tablero de ajedrez.
     *
     * @param f la fila de la celda
     * @param c la columna de la celda
     * @return el color de fondo de esa celda
     */
    // IA generativa para los colores verde oscuroy verde claro
    private Color colorDeFondo(int f, int c){
        if ((f + c) % 2 == 0){
            return new Color(34, 120, 50);    
        } else {
            return new Color(140, 210, 110); 
        }
    }

    /**
     * Pinta cada celda según lo que hay en el juego: cabeza, cuerpo,
     * fruta, obstáculo o fondo. También actualiza la etiqueta del tamaño.
     */
    private void refresh(){
        for (int f = 0; f<juego.getFilas(); f++){
            for (int c = 0; c<juego.getColumnas(); c++){
                if (juego.hayCabeza(f, c)){
                    celdas[f][c].setBackground(colorSerpiente.darker());
                }
                else if (juego.haySerpiente(f, c)){
                    celdas[f][c].setBackground(colorSerpiente); 
                }
                else if (juego.hayFruta(f, c)){
                    celdas[f][c].setBackground(colorFrutas);
                }
                else if (juego.hayObstaculo(f, c)){
                    celdas[f][c].setBackground(colorObstaculos);
                }
                else {
                    celdas[f][c].setBackground(colorDeFondo(f, c));
                } 

            }
        }
        tamano.setText("El tamaño de la serpiente es: " + juego.getTamano());
    }

    /**
     * Prepara el controlador: el cierre de la ventana con confirmación y
     * los oyentes del menú, del juego y de la pantalla de inicio.
     */
    private void prepareController(){
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                exit();
            }
        });
        prepareControllerMenu();
        prepareControllerGame();
        prepareControllerInicio();
    }

    /**
     * Prepara los oyentes de las opciones del menú.
     */
    private void prepareControllerMenu(){
        itemSalir.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e){
                exit();
            }
        });
        itemAbrir.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e){
                open();
            }
        });
        itemSalvar.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e){
                save();
            }
        });
        itemColorSerpiente.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                colorSerpiente = elegirColor("Color de la serpiente", colorSerpiente);
                refresh();
            }
        }));
        itemColorFrutas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                colorFrutas = elegirColor("Color de las frutas", colorFrutas);
                refresh();
            }
        });
        itemColorObstaculos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                colorObstaculos = elegirColor("Color de los obstáculos", colorObstaculos);
                refresh();
            }
        });
        itemNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                reiniciar();
            }
        });
        itemConfiguracion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                configurar();
            }
        });    
    }

    /**
     * Prepara los oyentes de los botones de la pantalla de inicio.
     * Play muestra el juego y arranca el reloj; Salir pide confirmación.
     */
    private void prepareControllerInicio(){
        botonSalir.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e){
                exit();
            }
        });
        botonPlay.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e){
                cartas.show(contenedor, "juego");
                reloj.start();
                requestFocusInWindow(); //IA generativa para poder devolver el foco
            }
        });
    }

    /**
     * Prepara el juego: el reloj que mueve la serpiente cada 200 ms y
     * las flechas del teclado para cambiar de dirección.
     */
    private void prepareControllerGame(){
        reloj = new Timer(200, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                paso();
            }
        });
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    juego.cambiarDireccion(Direction.NORTE);
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    juego.cambiarDireccion(Direction.SUR);
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT){
                    juego.cambiarDireccion(Direction.OESTE);
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                    juego.cambiarDireccion(Direction.ESTE);
                }
            }
        });
    }

    /**
     * Da un paso del juego: mueve la serpiente y refresca el tablero.
     * Si el jugador perdió, detiene el reloj y muestra el puntaje.
     */
    private void paso(){
        juego.mover();
        refresh();
        if (juego.isJuegoTerminado()){
            reloj.stop();
            JOptionPane.showMessageDialog(this, "Perdiste tu puntaje fue de: " + juego.getTamano());
        }
    }

    /**
     * Empieza una partida nueva con la misma configuración y arranca el reloj.
     * Si hay algún error, lo muestra en un mensaje.
     */
    private void reiniciar(){
        reloj.stop();
        try {
            juego = new HungrySnake(juego.getFilas(), juego.getColumnas(), juego.getNumeroFrutas(), juego.getPorcentajeObstaculos());
            refresh();
            cartas.show(contenedor, "juego");
            reloj.start();
            requestFocusInWindow();
        } catch(HungrySnakeException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al reiniciar", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra una ventana para cambiar las filas, las columnas, el número
     * de frutas y el porcentaje de obstáculos. Si los datos son válidos,
     * crea un juego nuevo y reconstruye el tablero. Pausa el reloj mientras
     * la ventana está abierta y lo vuelve a arrancar si estaba andando o se 
     * hicieron nuevos cambios.
     */
    private void configurar(){
        boolean relojEjecutando = reloj.isRunning();
        if (relojEjecutando){
            reloj.stop();
        }
        JTextField campoFilas = new JTextField(String.valueOf(juego.getFilas()));
        JTextField campoColumnas = new JTextField(String.valueOf(juego.getColumnas()));
        JTextField campoFrutas = new JTextField(String.valueOf(juego.getNumeroFrutas()));
        JTextField campoPorcentaje = new JTextField(String.valueOf(juego.getPorcentajeObstaculos()));
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Filas:"));
        panel.add(campoFilas);
        panel.add(new JLabel("Columnas :"));
        panel.add(campoColumnas);
        panel.add(new JLabel("Numero de frutas: "));
        panel.add(campoFrutas);
        panel.add(new JLabel("Porcentaje de obstaculos: "));
        panel.add(campoPorcentaje);
        int resultado = JOptionPane.showConfirmDialog(this, panel, "Configuración", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        boolean aplicado = false;
        if (resultado == JOptionPane.OK_OPTION){
            String textoFilas = campoFilas.getText();
            String textoColumnas = campoColumnas.getText();
            String textoFrutas = campoFrutas.getText();
            String textoObstaculos = campoPorcentaje.getText();
            try{
                int filasNuevas = Integer.parseInt(textoFilas.trim());
                int columnasNuevas = Integer.parseInt(textoColumnas.trim());
                int numeroFrutasNuevo = Integer.parseInt(textoFrutas.trim());
                double porcentajeObstaculosNuevo = Double.parseDouble(textoObstaculos.trim());
                HungrySnake nuevo = new HungrySnake(filasNuevas, columnasNuevas, numeroFrutasNuevo, porcentajeObstaculosNuevo);
                juego = nuevo;
                construirTablero();
                refresh();
                cartas.show(contenedor, "juego");
                aplicado = true;
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, HungrySnakeException.NUMBER_FORMAT, "Error por formato", JOptionPane.ERROR_MESSAGE);
            } catch (HungrySnakeException e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (relojEjecutando || aplicado){
            reloj.start();
        }
        requestFocusInWindow();
    }

    /**
     * Abre el selector de colores y devuelve el color elegido.
     *
     * @param titulo el título de la ventana del selector
     * @param actual el color actual, que se deja si el usuario cancela
     * @return el color elegido, o el actual si se canceló
     */
    private Color elegirColor(String titulo, Color actual){
        Color elegido = JColorChooser.showDialog(this, titulo, actual);
        if (elegido == null){
            return actual;
        }
        return elegido;
    }

    /**
     * Pide confirmación y, si el usuario dice que sí, termina el programa.
     */
    private void exit(){
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Estas seguro que deseas salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION){
            System.exit(0);
        }   
    }

    /**
     * Muestra el selector de archivos para abrir. Por ahora solo avisa que
     * la función está en construcción y dice el nombre del archivo elegido.
     */
    private void open(){
        JFileChooser selector = new JFileChooser();
        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION){
            File archivo = selector.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Abrir esta en construccion. Archivo: " + archivo.getName());
        }
    }

    /**
     * Muestra el selector de archivos para salvar. Por ahora solo avisa que
     * la función está en construcción y dice el nombre del archivo elegido.
     */
    private void save(){
        JFileChooser selector = new JFileChooser();
        int resultado = selector.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION){
            File archivo = selector.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Salvar esta en construccion. Archivo: " + archivo.getName());
        }
    }
}