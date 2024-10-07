
public class IngresarDatosVotante extends javax.swing.JFrame {
    private javax.swing.JFrame ventanaAnterior;
    private boolean esActualizar; 

    public IngresarDatosVotante(javax.swing.JFrame ventanaAnterior, boolean esActualizar) {
        this.ventanaAnterior = ventanaAnterior;
        this.esActualizar = esActualizar;
        initComponents();
        configurarVentana();
    }

    private void configurarVentana() {
        if (esActualizar) {
            setTitle("Actualizar Dirección del Votante");
            botonGuardarRUT.setText("Actualizar");
        } else {
            setTitle("Ingresar Datos del Votante");
            botonGuardarRUT.setText("Guardar");
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
        ingreseRutTEXTO = new javax.swing.JLabel();
        textoREGION = new javax.swing.JTextField();
        textoRUT = new javax.swing.JTextField();
        ingreseRutTEXTO2 = new javax.swing.JLabel();
        ingreseRutTEXTO1 = new javax.swing.JLabel();
        textoCOMUNA = new javax.swing.JTextField();
        botonGuardarRUT = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        botonVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ingreseRutTEXTO.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        ingreseRutTEXTO.setText("Rut:");

        textoREGION.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoREGIONActionPerformed(evt);
            }
        });

        ingreseRutTEXTO2.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        ingreseRutTEXTO2.setText("Comuna:");

        ingreseRutTEXTO1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        ingreseRutTEXTO1.setText("Region:");

        botonGuardarRUT.setText("Guardar");
        botonGuardarRUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarRUTActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ingrese sus datos:");

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
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ingreseRutTEXTO1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ingreseRutTEXTO2, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(ingreseRutTEXTO, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(textoCOMUNA, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                        .addComponent(textoREGION)
                        .addComponent(textoRUT)
                        .addComponent(botonGuardarRUT, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ingreseRutTEXTO, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoRUT, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ingreseRutTEXTO1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoREGION, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoCOMUNA, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ingreseRutTEXTO2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(botonGuardarRUT)
                .addGap(18, 18, 18)
                .addComponent(botonVolver)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVolverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonVolverMouseClicked
        cerrarVentana();
    }//GEN-LAST:event_botonVolverMouseClicked

    private void botonGuardarRUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarRUTActionPerformed
        
        String rut = textoRUT.getText();
        String region = textoREGION.getText();
        String comuna = textoCOMUNA.getText();

        if (esActualizar) {
            // Llamar al método para actualizar la dirección del votante
            Proyecto.actualizarDireccionVotanteDesdeGUI(rut, region, comuna);
        } else {
            // Llamar al método para ingresar un nuevo votante
            Proyecto.ingresarVotanteDesdeGUI(rut, region, comuna);
        }

        // Cerrar la ventana actual y volver a la ventana anterior
        cerrarVentana();
    }//GEN-LAST:event_botonGuardarRUTActionPerformed

    private void textoREGIONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoREGIONActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoREGIONActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonGuardarRUT;
    private javax.swing.JButton botonVolver;
    private javax.swing.JLabel ingreseRutTEXTO;
    private javax.swing.JLabel ingreseRutTEXTO1;
    private javax.swing.JLabel ingreseRutTEXTO2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField textoCOMUNA;
    private javax.swing.JTextField textoREGION;
    private javax.swing.JTextField textoRUT;
    // End of variables declaration//GEN-END:variables
}
