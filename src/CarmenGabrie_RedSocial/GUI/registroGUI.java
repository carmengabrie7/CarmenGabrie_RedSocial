package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Usuario;
import CarmenGabrie_RedSocial.UsuarioManager;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class registroGUI extends JFrame{
    private JTextField txtNombre;
    private JComboBox <String> comboGenero;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JTextField txtEdad;
    private JComboBox<String> comboCuenta;
    
    private UsuarioManager usuarioManager;
    
    public registroGUI(){
        try{
            usuarioManager = new UsuarioManager();
        } catch (IOException e){
            e.printStackTrace();
        }
        
        setTitle("Instagram");
        setSize (900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel fondo = new JPanel();
        fondo.setLayout(null);
        fondo.setBackground(Color.white);
        setContentPane(fondo);
        
        JLabel titulo = new JLabel("Empieza a usar Instagram");
        titulo.setFont(new Font("Arial",Font.BOLD,20));
        titulo.setBounds(60, 40, 300, 30);
        fondo.add(titulo);
        
        JLabel subtitulo = new JLabel("Registrate para ver fotos y videos de tus amigos.");
        subtitulo.setBounds(60,70,350,30);
        fondo.add(subtitulo);
        
        JLabel nombre = new JLabel ("Nombre Completo");
        nombre.setBounds(60,130,150,25);
        fondo.add(nombre);
        
        txtNombre = new JTextField();
        txtNombre.setBounds(60,160,280,35);
        fondo.add(txtNombre);
        
        JLabel genero = new JLabel ("Genero (M/F)");
        genero.setBounds(60,210,150,25);
        fondo.add(genero);
        
        String[] opcionesGenero = {"M","F"};
        
        comboGenero = new JComboBox<>(opcionesGenero);
        comboGenero.setBounds(60,240,100,35);
        fondo.add(comboGenero);
        
        JLabel usuario = new JLabel("Usuario");
        usuario.setBounds(60,290,150,25);
        fondo.add(usuario);
        
        txtUser = new JTextField();
        txtUser.setBounds(60,320,280,35);
        fondo.add(txtUser);
        
        JLabel pass = new JLabel ("Contraseña");
        pass.setBounds(60,370,150,25);
        fondo.add(pass);
        
        txtPass = new JPasswordField();
        txtPass.setBounds(60,400,280,35);
        fondo.add(txtPass);
        
        JLabel edad = new JLabel ("Edad");
        edad.setBounds(60,450,150,25);
        fondo.add(edad);
        
        txtEdad = new JTextField();
        txtEdad.setBounds(60,480,80,35);
        fondo.add(txtEdad);
        
        JLabel cuenta = new JLabel ("Tipo de cuenta");
        cuenta.setBounds(420,210,150,25);
        fondo.add(cuenta);
        
        String[] tiposCuenta = {"Publica","Privada"};
        
        comboCuenta = new JComboBox <> (tiposCuenta);
        comboCuenta.setBounds(420,240,150,35);
        fondo.add(comboCuenta);
        
        JButton guardar = new JButton("Guardar cuenta");
        guardar.setBounds(420,450,250,45);
        guardar.setBackground(new Color(22,119,225));
        fondo.add(guardar);
        
        guardar.addActionListener(e-> crearUsuario());
        
        setVisible(true);
    }
    
    private void crearUsuario(){
        try {
            if (txtNombre.getText().isEmpty() || 
                txtUser.getText().isEmpty() ||
                txtPass.getPassword().length ==0 ||
                txtEdad.getText().isEmpty()) {
                
                JOptionPane.showMessageDialog(this, "Todos los campos deben llenarse");
                return;
        }
        
        int edad;
        
        try {
            edad = Integer.parseInt(txtEdad.getText());
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "La edad debe ser un numero");
            return;
        }
        
        String user = txtUser.getText();
        
        if (usuarioManager.userExists(user)){
            JOptionPane.showMessageDialog(this, "Ese usuario ya existe");
            return;
        }
        
        String nombre= txtNombre.getText();
        char genero = comboGenero.getSelectedItem().toString().charAt(0);
        String pass = new String(txtPass.getPassword());
        
        boolean privada = comboCuenta.getSelectedItem().equals("Privada");
        
        long fecha = System.currentTimeMillis();
        
        Usuario u = new Usuario(
                nombre,
                genero,
                user,
                pass,
                fecha,
                edad,
                true,
                "default.jpg",
                privada
        );
        
        usuarioManager.createUser(u);
        
        JOptionPane.showMessageDialog(this, "Cuenta creada correctamente");
        
        new inicioGUI();
        dispose();
        
    } catch (Exception e){
    e.printStackTrace();
    }}}
