
import javax.swing.JOptionPane;


public class IngreseRutBuscar extends javax.swing.JFrame {
    private javax.swing.JFrame ventanaAnterior;
    private String tipoOperacion;

    public IngreseRutBuscar(javax.swing.JFrame ventanaAnterior, String tipoOperacion) {
        this.ventanaAnterior = ventanaAnterior;
        this.tipoOperacion = tipoOperacion;
        initComponents();
        configurarVentana();
    }

private void configurarVentana() {
    switch (tipoOperacion) {
        case "eliminarVotante":
            setTitle("Eliminar Votante");
            jLabel1.setText("Ingrese el RUT:");
            break;
        case "eliminarLocal":
            setTitle("Eliminar Local");
            jLabel1.setText("Ingrese nombre del local:");
            break;
        case "mostrarDatos":
            setTitle("Mostrar Datos del Votante");
            jLabel1.setText("Ingrese el RUT:");
            break;
        default:
            setTitle("Operación Desconocida");
            jLabel1.setText("Ingrese el valor:");
            break;
    }
}

    
    private void cerrarVentana() {
        this.dispose();
        if (ventanaAnterior != null) {
            ventanaAnterior.setVisible(true);
        }
    }
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textoRUT = new javax.swing.JTextField();
        oki = new javax.swing.JButton();
        botonVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ingrese el rut:");

        oki.setText("OK");
        oki.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okiMouseClicked(evt);
            }
        });

        botonVolver.setText("Atras");
        botonVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonVolverMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonVolver)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textoRUT, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oki)))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoRUT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oki))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonVolver)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVolverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonVolverMouseClicked

        cerrarVentana();
    }//GEN-LAST:event_botonVolverMouseClicked

    private void okiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okiMouseClicked

        String input = textoRUT.getText(); // Utilizar el campo para ingresar el RUT o el nombre del local

        switch (tipoOperacion) {
            case "eliminarVotante":
                Proyecto.eliminarVotanteDesdeGUI(input);
                break;
            case "eliminarLocal":
                Proyecto.eliminarLocalDesdeGUI(input);
                break;
            case "mostrarDatos":
                Proyecto.mostrarVotanteDesdeGUI(input);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Operación no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }

        // Opcional: Cerrar la ventana actual y regresar a la ventana anterior
        cerrarVentana();
    }//GEN-LAST:event_okiMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton oki;
    private javax.swing.JTextField textoRUT;
    // End of variables declaration//GEN-END:variables
}
