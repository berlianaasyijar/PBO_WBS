package TugasBesarWBS;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AplikasiWBS {
    private JTextField RTtextField;
    private JTextField RWtextField;
    private JButton saveButton;
    private JButton clearButton;
    private JTextField NIKtextField;
    private JTextField NamatextField;
    private JTextField TempLahirtextField;
    private JTextField TangLahirtextField;
    private JTextField PendtextField;
    private JTextField BPJStextField;
    private JTextField AgamatextField;
    private JTextField PekerjaantextField;
    private JTextField KelurahantextField;
    private JTextField KectextField;
    private JTextField KotatextField;
    private JTextField AsdatextField;
    private JRadioButton lakiLakiRadioButton;
    private JRadioButton perempuanRadioButton;
    private JTextField AlamattextField;
    private JTextField JurtextField;
    private JTextField NamaWalitextField;
    private JTextField AlamatWalitextField;
    private JTextField HubKeltextField;
    private JTextField KettextField;
    private JRadioButton lakiLakiRadioButton2;
    private JRadioButton perempuanRadioButton1;
    private JButton saveButton1;
    private JButton clearButton1;
    private JTable tableWarga;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JRadioButton lakiLakiRadioButton1;
    private JButton demoDataButton;
    private JButton demoDataButton1;
    private JLabel nikjlabel;
    private JLabel namajlabel;
    private JLabel jkpasienjlabel;
    private JLabel tempatlahirljlabel;
    private JLabel tanggallahirjlabel;
    private JLabel pendtrkhhrjlabel;
    private JLabel nobpjsjlabel;
    private JLabel agamajlabel;
    private JLabel pekerjaanjlabel;
    private JLabel alamatjlabel;
    private JLabel rtjlabel;
    private JLabel kelurahanjlabel;
    private JLabel kecjlabel;
    private JLabel kotajlabel;
    private JLabel asdajlabel;
    private JPanel datakeluargaPanel;
    private JPanel datawargabinaPanel;
    private JScrollPane ScrollWarga;
    private JTable tableKeluarga;
    private JScrollPane ScrollKeluarga;
    private JTextField searchfield;
    private JTextField NIKWargahal2;
    private JButton editDataButton;
    private JTextField updatewithNIK;
    private JButton deleteDataNamaButton;
    private JButton deleteDataNIK;
    private JButton updateDataButton;
    private JButton updateDataButton1;
    private JButton deleteDataNIKButton1;
    private JButton editDataNIKButton1;
    private JTextField updatewithNIK1;
    private DefaultTableModel model;
    private static Connection c;
    private static Statement s;
    private static ResultSet rs;

    public enum JenisKelamin {
        Lakilaki("Laki-laki"),
        Perempuan("Perempuan");

        private final String value;

        JenisKelamin(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum JenisKelaminWali {
        laki_laki("Laki-laki"),
        perempuan("Perempuan");

        private final String value;

        JenisKelaminWali(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Aplikasi Panti Sosial Bina Remaja Taruna Jaya 2");
        frame.setContentPane(new AplikasiWBS().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/db_app_wbs", "root", "");
            System.out.println("Success");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }

    void TableWargaBinaan() {
        try {
            pst = con.prepareStatement("SELECT * FROM warga");
            ResultSet rs = pst.executeQuery();
            tableWarga.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void TabelKeluarga() {
        try {
            pst = con.prepareStatement("SELECT * FROM keluarga_wargabinaan");
            ResultSet rs = pst.executeQuery();
            tableKeluarga.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public AplikasiWBS() {
        connect();
        TableWargaBinaan();
        TabelKeluarga();

        ButtonGroup JenisKelaminPasien = new ButtonGroup();
        JenisKelaminPasien.add(lakiLakiRadioButton);
        JenisKelaminPasien.add(perempuanRadioButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nik, nama, tempatlahir, pendidikanterakhir, jurusanterakhir, nobpjs, agama, pekerjaan, alamat, rt, rw, kelurahan, kecamatan,
                        kota, asaldaerah;
                JenisKelamin jk;

                Date tanggallahir = null;

                nik = NIKtextField.getText();
                nama = NamatextField.getText();
                jk = lakiLakiRadioButton.isSelected() ? JenisKelamin.Lakilaki : JenisKelamin.Perempuan;
                tempatlahir = TempLahirtextField.getText();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    java.util.Date parsedDate = dateFormat.parse(TangLahirtextField.getText());
                    tanggallahir = new Date(parsedDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "tanggal tidak sesuai dengan format yyyy-MM-dd");
                }
                pendidikanterakhir = PendtextField.getText();
                jurusanterakhir = JurtextField.getText();
                nobpjs = BPJStextField.getText();
                agama = AgamatextField.getText();
                pekerjaan = PekerjaantextField.getText();

                alamat = AlamattextField.getText();
                rt = RTtextField.getText();
                rw = RWtextField.getText();
                kelurahan = KelurahantextField.getText();
                kecamatan = KectextField.getText();
                kota = KotatextField.getText();

                asaldaerah = AsdatextField.getText();

                try {
                    // Cek apakah data dengan NIK yang sama sudah ada di database
                    pst = con.prepareStatement("SELECT * FROM warga WHERE nik = ?");
                    pst.setString(1, nik);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Data Warga Binaan dengan NIK " + nik + " sudah ada!");
                    } else {
                        // Data dengan NIK tersebut belum ada, lakukan operasi INSERT
                        pst = con.prepareStatement("INSERT INTO warga(nik, nama, jk, tempatlahir, tanggallahir, pendidikanterakhir, jurusanterakhir, nobpjs, agama, pekerjaan, alamat, rt, rw, kelurahan, kecamatan, kota, asaldaerah) " +
                                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        pst.setString(1, nik);
                        pst.setString(2, nama);
                        String jenisKelamin = jk == jk.Lakilaki ? "Laki-laki" : "Perempuan";
                        pst.setString(3, jenisKelamin);
                        pst.setString(4, tempatlahir);
                        pst.setString(5, String.valueOf(tanggallahir));
                        pst.setString(6, pendidikanterakhir);
                        pst.setString(7, jurusanterakhir);
                        pst.setString(8, nobpjs);
                        pst.setString(9, agama);
                        pst.setString(10, pekerjaan);
                        pst.setString(11, alamat);
                        pst.setString(12, rt);
                        pst.setString(13, rw);
                        pst.setString(14, kelurahan);
                        pst.setString(15, kecamatan);
                        pst.setString(16, kota);
                        pst.setString(17, asaldaerah);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data Warga Binaan Telah Masuk!");

                        TableWargaBinaan();
                        NIKtextField.setText("");
                        NamatextField.setText("");
                        JenisKelaminPasien.clearSelection();
                        TempLahirtextField.setText("");
                        TangLahirtextField.setText("");
                        PendtextField.setText("");
                        JurtextField.setText("");
                        BPJStextField.setText("");
                        AgamatextField.setText("");
                        PekerjaantextField.setText("");
                        AlamattextField.setText("");
                        RTtextField.setText("");
                        RWtextField.setText("");
                        KelurahantextField.setText("");
                        KectextField.setText("");
                        KotatextField.setText("");
                        AsdatextField.setText("");

                        NIKtextField.requestFocus();
                        NamatextField.requestFocus();
                        TempLahirtextField.requestFocus();
                        TangLahirtextField.requestFocus();
                        PendtextField.requestFocus();
                        JurtextField.requestFocus();
                        BPJStextField.requestFocus();
                        AgamatextField.requestFocus();
                        PekerjaantextField.requestFocus();
                        AlamattextField.requestFocus();
                        RTtextField.requestFocus();
                        RWtextField.requestFocus();
                        KelurahantextField.requestFocus();
                        KectextField.requestFocus();
                        KotatextField.requestFocus();
                        AsdatextField.requestFocus();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam memeriksa data Warga Binaan!");
                }
            }
        });

        demoDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                NIKtextField.setText("3209140806980006");
                NamatextField.setText("Loreal Marhambito");
                lakiLakiRadioButton.setSelected(true);
                TempLahirtextField.setText("Bandung");
                TangLahirtextField.setText("1998-06-08");
                PendtextField.setText("SLTA Sederajat");
                JurtextField.setText("IPA");
                BPJStextField.setText("0874660394567");
                AgamatextField.setText("Hindu");
                PekerjaantextField.setText("Pegawai BUMN");

                AlamattextField.setText("Jalan Margonda Raya, No. 13, Jakarta Pusat");
                RTtextField.setText("009");
                RWtextField.setText("012");
                KelurahantextField.setText("Tanah Abang");
                KectextField.setText("Margonda");
                KotatextField.setText("Jakarta Pusat");
                AsdatextField.setText("Majalaya, Bandung");
            }
        });

        ButtonGroup JKWaliGroup = new ButtonGroup();
        JKWaliGroup.add(lakiLakiRadioButton1);
        JKWaliGroup.add(perempuanRadioButton1);

        saveButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nik, namawali, alamatwali, hubkel, keterangan;
                JenisKelaminWali jkwali;

                nik = NIKWargahal2.getText();
                namawali = NamaWalitextField.getText();
                jkwali = lakiLakiRadioButton1.isSelected() ? JenisKelaminWali.laki_laki : JenisKelaminWali.perempuan;
                alamatwali = AlamatWalitextField.getText();
                hubkel = HubKeltextField.getText();
                keterangan = KettextField.getText();

                try {
                    // Cek apakah data dengan NIK yang sama sudah ada di database
                    pst = con.prepareStatement("SELECT * FROM keluarga_wargabinaan WHERE nik = ?");
                    pst.setString(1, nik);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Data Keluarga Binaan dengan NIK " + nik + " sudah ada!");
                    } else {
                        // Data dengan NIK tersebut belum ada, lakukan operasi INSERT
                        pst = con.prepareStatement("INSERT INTO keluarga_wargabinaan(nik, namawali, jkwali, alamatwali, hubkeluarga, keterangan) " +
                                "values (?, ?, ?, ?, ?, ?)");
                        pst.setString(1, nik);
                        pst.setString(2, namawali);
                        String jenisKelaminWali = jkwali == jkwali.laki_laki ? "Laki-laki" : "Perempuan";
                        pst.setString(3, jenisKelaminWali);
                        pst.setString(4, alamatwali);
                        pst.setString(5, hubkel);
                        pst.setString(6, keterangan);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data Keluarga Binaan Telah Masuk!");

                        TabelKeluarga();
                        NIKWargahal2.setText("");
                        NamaWalitextField.setText("");
                        JKWaliGroup.clearSelection();
                        AlamatWalitextField.setText("");
                        HubKeltextField.setText("");
                        KettextField.setText("");

                        NIKWargahal2.requestFocus();
                        NamaWalitextField.requestFocus();
                        AlamattextField.requestFocus();
                        HubKeltextField.requestFocus();
                        KettextField.requestFocus();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "NIK " + nik + " Tidak sesuai! Harap periksa kembali.");
                }
            }
        });


        clearButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                NIKWargahal2.setText("");
                NamaWalitextField.setText("");
                JKWaliGroup.clearSelection();
                AlamatWalitextField.setText("");
                HubKeltextField.setText("");
                KettextField.setText("");
            }
        });
        demoDataButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                NIKWargahal2.setText("3209140806980006");
                NamaWalitextField.setText("Haji Muhidin");
                lakiLakiRadioButton1.setSelected(true);
                AlamatWalitextField.setText("Jalan Mertapada No. 114 Kota Bandung");
                HubKeltextField.setText("Ayah Kandung");
                KettextField.setText("Terkena kasus perundungan di SMA");
            }
        });
        deleteDataNIK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nik;
                nik = updatewithNIK.getText();

                try {
                    if (nik.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Masukkan NIK yang sesuai!");
                    } else {
                        pst = con.prepareStatement("DELETE FROM keluarga_wargabinaan WHERE nik = ?");
                        pst.setString(1, nik);
                        int rowsDeleted = pst.executeUpdate();

                        if (rowsDeleted > 0) {
                            pst = con.prepareStatement("DELETE FROM warga  WHERE nik = ?");
                            pst.setString(1, nik);
                            pst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Record Delete!");

                            TableWargaBinaan();
                            TabelKeluarga();
                            NIKtextField.setText("");
                            NamatextField.setText("");
                            JenisKelaminPasien.clearSelection();
                            TempLahirtextField.setText("");
                            TangLahirtextField.setText("");
                            PendtextField.setText("");
                            JurtextField.setText("");
                            BPJStextField.setText("");
                            AgamatextField.setText("");
                            PekerjaantextField.setText("");
                            AlamattextField.setText("");
                            RTtextField.setText("");
                            RWtextField.setText("");
                            KelurahantextField.setText("");
                            KectextField.setText("");
                            KotatextField.setText("");
                            AsdatextField.setText("");
                            NIKtextField.requestFocus();
                        } else {
                            JOptionPane.showMessageDialog(null, "Tolong masukkan NIK yang tersedia di data");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam menghapus data!");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NIKtextField.setText("");
                NamatextField.setText("");
                JenisKelaminPasien.clearSelection();
                TempLahirtextField.setText("");
                TangLahirtextField.setText("");
                PendtextField.setText("");
                JurtextField.setText("");
                BPJStextField.setText("");
                AgamatextField.setText("");
                PekerjaantextField.setText("");

                AlamattextField.setText("");
                RTtextField.setText("");
                RWtextField.setText("");
                KelurahantextField.setText("");
                KectextField.setText("");
                KotatextField.setText("");
                AsdatextField.setText("");
            }
        });
        editDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nik = updatewithNIK.getText();

                try {
                    pst = con.prepareStatement("SELECT * FROM warga WHERE nik = ?");
                    pst.setString(1, nik);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        // Mengisi nilai ke field sesuai dengan data yang ditemukan
                        NIKtextField.setText(rs.getString("nik"));
                        NamatextField.setText(rs.getString("nama"));
                        // Mengatur nilai radio button berdasarkan jenis kelamin
                        String jenisKelamin = rs.getString("jk");
                        lakiLakiRadioButton.setSelected(jenisKelamin.equalsIgnoreCase("Laki-Laki"));
                        perempuanRadioButton.setSelected(jenisKelamin.equalsIgnoreCase("Perempuan"));
                        TempLahirtextField.setText(rs.getString("tempatlahir"));
                        TangLahirtextField.setText(rs.getString("tanggallahir"));
                        PendtextField.setText(rs.getString("pendidikanterakhir"));
                        JurtextField.setText(rs.getString("jurusanterakhir"));
                        BPJStextField.setText(rs.getString("nobpjs"));
                        AgamatextField.setText(rs.getString("agama"));
                        PekerjaantextField.setText(rs.getString("pekerjaan"));
                        AlamattextField.setText(rs.getString("alamat"));
                        RTtextField.setText(rs.getString("rt"));
                        RWtextField.setText(rs.getString("rw"));
                        KelurahantextField.setText(rs.getString("kelurahan"));
                        KectextField.setText(rs.getString("kecamatan"));
                        KotatextField.setText(rs.getString("kota"));
                        AsdatextField.setText(rs.getString("asaldaerah"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Data dengan NIK " + nik + " tidak ditemukan!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam mengambil data!");
                }
            }
        });
        updateDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nik = NIKtextField.getText();
                String nama = NamatextField.getText();
                String jenisKelamin = lakiLakiRadioButton.isSelected() ? "Laki-Laki" : "Perempuan";
                String tempatLahir = TempLahirtextField.getText();
                String tanggalLahir = TangLahirtextField.getText();
                String pendidikan = PendtextField.getText();
                String jurusan = JurtextField.getText();
                String bpjs = BPJStextField.getText();
                String agama = AgamatextField.getText();
                String pekerjaan = PekerjaantextField.getText();
                String alamat = AlamattextField.getText();
                String rt = RTtextField.getText();
                String rw = RWtextField.getText();
                String kelurahan = KelurahantextField.getText();
                String kecamatan = KectextField.getText();
                String kota = KotatextField.getText();
                String asalDaerah = AsdatextField.getText();

                try {
                    pst = con.prepareStatement("SELECT * FROM warga WHERE nik=?");
                    pst.setString(1, nik);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        boolean dataChanged = false;

                        String existingNama = rs.getString("nama");
                        String existingJenisKelamin = rs.getString("jk");
                        String existingTempatLahir = rs.getString("tempatlahir");
                        String existingTanggalLahir = rs.getString("tanggallahir");
                        String existingPendidikan = rs.getString("pendidikanterakhir");
                        String existingJurusan = rs.getString("jurusanterakhir");
                        String existingBpjs = rs.getString("nobpjs");
                        String existingAgama = rs.getString("agama");
                        String existingPekerjaan = rs.getString("pekerjaan");
                        String existingAlamat = rs.getString("alamat");
                        String existingRt = rs.getString("rt");
                        String existingRw = rs.getString("rw");
                        String existingKelurahan = rs.getString("kelurahan");
                        String existingKecamatan = rs.getString("kecamatan");
                        String existingKota = rs.getString("kota");
                        String existingAsalDaerah = rs.getString("asaldaerah");

                        if (!nama.equals(existingNama) ||
                                !jenisKelamin.equals(existingJenisKelamin) ||
                                !tempatLahir.equals(existingTempatLahir) ||
                                !tanggalLahir.equals(existingTanggalLahir) ||
                                !pendidikan.equals(existingPendidikan) ||
                                !jurusan.equals(existingJurusan) ||
                                !bpjs.equals(existingBpjs) ||
                                !agama.equals(existingAgama) ||
                                !pekerjaan.equals(existingPekerjaan) ||
                                !alamat.equals(existingAlamat) ||
                                !rt.equals(existingRt) ||
                                !rw.equals(existingRw) ||
                                !kelurahan.equals(existingKelurahan) ||
                                !kecamatan.equals(existingKecamatan) ||
                                !kota.equals(existingKota) ||
                                !asalDaerah.equals(existingAsalDaerah)) {
                            dataChanged = true;
                        }

                        if (dataChanged) {
                            pst = con.prepareStatement("UPDATE warga SET nama=?, jk=?, tempatlahir=?, tanggallahir=?, pendidikanterakhir=?, jurusanterakhir=?, nobpjs=?, agama=?, pekerjaan=?, alamat=?, rt=?, rw=?, kelurahan=?, kecamatan=?, kota=?, asaldaerah=? WHERE nik=?");
                            pst.setString(1, nama);
                            pst.setString(2, jenisKelamin);
                            pst.setString(3, tempatLahir);
                            pst.setString(4, tanggalLahir);
                            pst.setString(5, pendidikan);
                            pst.setString(6, jurusan);
                            pst.setString(7, bpjs);
                            pst.setString(8, agama);
                            pst.setString(9, pekerjaan);
                            pst.setString(10, alamat);
                            pst.setString(11, rt);
                            pst.setString(12, rw);
                            pst.setString(13, kelurahan);
                            pst.setString(14, kecamatan);
                            pst.setString(15, kota);
                            pst.setString(16, asalDaerah);
                            pst.setString(17, nik);
                            int rowsUpdated = pst.executeUpdate();

                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
                                TableWargaBinaan();
                                TabelKeluarga();
                                NIKtextField.setText("");
                                NamatextField.setText("");
                                JenisKelaminPasien.clearSelection();
                                TempLahirtextField.setText("");
                                TangLahirtextField.setText("");
                                PendtextField.setText("");
                                JurtextField.setText("");
                                BPJStextField.setText("");
                                AgamatextField.setText("");
                                PekerjaantextField.setText("");
                                AlamattextField.setText("");
                                RTtextField.setText("");
                                RWtextField.setText("");
                                KelurahantextField.setText("");
                                KectextField.setText("");
                                KotatextField.setText("");
                                AsdatextField.setText("");
                                NIKtextField.requestFocus();
                            } else {
                                JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam memperbarui data!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Tidak ada perubahan dalam data!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "NIK " + nik + " tidak ditemukan! Mohon isi yang sesuai dengan data yang ada");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam memperbarui data!");
                }
            }
        });
        deleteDataNIKButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nik;
                nik = updatewithNIK1.getText();

                try {
                    if (nik.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Masukkan NIK yang sesuai!");
                    } else {
                        pst = con.prepareStatement("DELETE FROM keluarga_wargabinaan WHERE nik = ?");
                        pst.setString(1, nik);
                        int rowsDeleted = pst.executeUpdate();

                        if (rowsDeleted > 0) {
                            pst = con.prepareStatement("DELETE FROM warga  WHERE nik = ?");
                            pst.setString(1, nik);
                            pst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Record Delete!");

                            TableWargaBinaan();
                            TabelKeluarga();
                            NIKWargahal2.setText("");
                            NamaWalitextField.setText("");
                            JKWaliGroup.clearSelection();
                            AlamatWalitextField.setText("");
                            HubKeltextField.setText("");
                            KettextField.setText("");
                            NIKWargahal2.requestFocus();
                        } else {
                            JOptionPane.showMessageDialog(null, "Tolong masukkan NIK yang tersedia di data");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam menghapus data!");
                }
            }
        });
        editDataNIKButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nik = updatewithNIK1.getText();

                try {
                    pst = con.prepareStatement("SELECT * FROM keluarga_wargabinaan WHERE nik = ?");
                    pst.setString(1, nik);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        // Mengisi nilai ke field sesuai dengan data yang ditemukan
                        NIKWargahal2.setText(rs.getString("nik"));
                        NamaWalitextField.setText(rs.getString("namawali"));
                        // Mengatur nilai radio button berdasarkan jenis kelamin
                        String jenisKelamin = rs.getString("jkwali");
                        lakiLakiRadioButton1.setSelected(jenisKelamin.equalsIgnoreCase("Laki-Laki"));
                        perempuanRadioButton1.setSelected(jenisKelamin.equalsIgnoreCase("Perempuan"));
                        AlamatWalitextField.setText(rs.getString("alamatwali"));
                        HubKeltextField.setText(rs.getString("hubkeluarga"));
                        KettextField.setText(rs.getString("keterangan"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Data dengan NIK " + nik + " tidak ditemukan!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam mengambil data!");
                }
            }
        });
        updateDataButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nik = NIKWargahal2.getText();
                String namawali = NamaWalitextField.getText();
                String jenisKelaminwali = lakiLakiRadioButton1.isSelected() ? "Laki-Laki" : "Perempuan";
                String alamatWali = AlamatWalitextField.getText();
                String hubKel = HubKeltextField.getText();
                String keterangan = KettextField.getText();

                try {
                    pst = con.prepareStatement("SELECT * FROM keluarga_wargabinaan WHERE nik=?");
                    pst.setString(1, nik);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        boolean dataChanged = false;

                        String existingNamaWali = rs.getString("namawali");
                        String existingJenisKelamin = rs.getString("jkwali");
                        String existingAlamatWali = rs.getString("alamatwali");
                        String existingHubKel = rs.getString("hubkeluarga");
                        String existingKeterangan = rs.getString("keterangan");

                        if (!namawali.equals(existingNamaWali) ||
                                !jenisKelaminwali.equals(existingJenisKelamin) ||
                                !alamatWali.equals(existingAlamatWali) ||
                                !hubKel.equals(existingHubKel) ||
                                !keterangan.equals(existingKeterangan)) {
                            dataChanged = true;
                        }

                        if (dataChanged) {
                            pst = con.prepareStatement("UPDATE keluarga_wargabinaan SET namawali=?, jkwali=?, alamatwali=?, hubkeluarga=?, keterangan=? WHERE nik=?");
                            pst.setString(1, namawali);
                            pst.setString(2, jenisKelaminwali);
                            pst.setString(3, alamatWali);
                            pst.setString(4, hubKel);
                            pst.setString(5, keterangan);
                            pst.setString(6, nik);
                            int rowsUpdated = pst.executeUpdate();

                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
                                TableWargaBinaan();
                                TabelKeluarga();
                                NIKWargahal2.setText("");
                                NamaWalitextField.setText("");
                                JKWaliGroup.clearSelection();
                                AlamatWalitextField.setText("");
                                HubKeltextField.setText("");
                                KettextField.setText("");
                                NIKWargahal2.requestFocus();
                            } else {
                                JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam memperbarui data!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Tidak ada perubahan dalam data!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "NIK " + nik + " tidak ditemukan! Mohon isi yang sesuai dengan data yang ada");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam memperbarui data!");
                }
            }
        });
    }
}