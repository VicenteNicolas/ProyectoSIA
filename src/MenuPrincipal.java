
public class MenuPrincipal extends javax.swing.JFrame {

    public MenuPrincipal() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        botonEliminarVotante = new javax.swing.JButton();
        botonMostrarDatos = new javax.swing.JButton();
        botonActualizarDirecc = new javax.swing.JButton();
        botonEliminarLocal = new javax.swing.JButton();
        botonIngresarVotante = new javax.swing.JButton();
        botonGenerarReporte = new javax.swing.JButton();
        botonMostrarLocales = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bienvenido al gestor de votantes");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Elige una opcion:");

        botonEliminarVotante.setBackground(new java.awt.Color(204, 204, 204));
        botonEliminarVotante.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botonEliminarVotante.setText("Eliminar votante");
        botonEliminarVotante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonEliminarVotanteMouseClicked(evt);
            }
        });

        botonMostrarDatos.setBackground(new java.awt.Color(204, 204, 204));
        botonMostrarDatos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botonMostrarDatos.setText("Mostrar datos del votante");
        botonMostrarDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonMostrarDatosMouseClicked(evt);
            }
        });

        botonActualizarDirecc.setBackground(new java.awt.Color(204, 204, 204));
        botonActualizarDirecc.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botonActualizarDirecc.setText("Actualizar direccion del votante");
        botonActualizarDirecc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonActualizarDireccMouseClicked(evt);
            }
        });

        botonEliminarLocal.setBackground(new java.awt.Color(204, 204, 204));
        botonEliminarLocal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botonEliminarLocal.setText("Eliminar local de votacion");
        botonEliminarLocal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonEliminarLocalMouseClicked(evt);
            }
        });

        botonIngresarVotante.setBackground(new java.awt.Color(204, 204, 204));
        botonIngresarVotante.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botonIngresarVotante.setText("Ingresar votante");
        botonIngresarVotante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonIngresarVotanteMouseClicked(evt);
            }
        });

        botonGenerarReporte.setBackground(new java.awt.Color(204, 204, 204));
        botonGenerarReporte.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botonGenerarReporte.setText("Generar reporte de votantes");
        botonGenerarReporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonGenerarReporteMouseClicked(evt);
            }
        });

        botonMostrarLocales.setBackground(new java.awt.Color(204, 204, 204));
        botonMostrarLocales.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botonMostrarLocales.setText("Mostrar locales de votacion");
        botonMostrarLocales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonMostrarLocalesMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(49, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonMostrarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonIngresarVotante, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonActualizarDirecc, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonEliminarVotante, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonMostrarLocales, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonEliminarLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonGenerarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(45, 45, 45))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonIngresarVotante, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonEliminarVotante, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(botonActualizarDirecc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonMostrarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonMostrarLocales, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonEliminarLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonGenerarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonIngresarVotanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonIngresarVotanteMouseClicked
        
        IngresarDatosVotante nuevaVentana = new IngresarDatosVotante(this, false);
        nuevaVentana.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_botonIngresarVotanteMouseClicked
  
    private void botonActualizarDireccMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonActualizarDireccMouseClicked

        IngresarDatosVotante nuevaVentana = new IngresarDatosVotante(this, true);
        nuevaVentana.setVisible(true);
        this.setVisible(false);
    
    }//GEN-LAST:event_botonActualizarDireccMouseClicked
  
    private void botonEliminarVotanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEliminarVotanteMouseClicked

        IngreseRutBuscar nuevaVentana = new IngreseRutBuscar(this, "eliminarVotante");
        nuevaVentana.setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_botonEliminarVotanteMouseClicked
  
    private void botonMostrarDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonMostrarDatosMouseClicked

        IngreseRutBuscar nuevaVentana = new IngreseRutBuscar(this, "mostrarDatos");
        nuevaVentana.setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_botonMostrarDatosMouseClicked

    private void botonGenerarReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonGenerarReporteMouseClicked

        Proyecto.generarReporteVotantesDesdeGUI();
    }//GEN-LAST:event_botonGenerarReporteMouseClicked

    private void botonEliminarLocalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEliminarLocalMouseClicked

        IngreseRutBuscar nuevaVentana = new IngreseRutBuscar(this, "eliminarLocal");
        nuevaVentana.setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_botonEliminarLocalMouseClicked

    private void botonMostrarLocalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonMostrarLocalesMouseClicked
        
        ElegirVentana nuevaVentana = new ElegirVentana();
        nuevaVentana.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_botonMostrarLocalesMouseClicked

    
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonActualizarDirecc;
    private javax.swing.JButton botonEliminarLocal;
    private javax.swing.JButton botonEliminarVotante;
    private javax.swing.JButton botonGenerarReporte;
    private javax.swing.JButton botonIngresarVotante;
    private javax.swing.JButton botonMostrarDatos;
    private javax.swing.JButton botonMostrarLocales;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
