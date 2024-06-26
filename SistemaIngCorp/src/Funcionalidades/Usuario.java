package Funcionalidades;

import BaseDeDatos.FuncionesObligatorias;
import BaseDeDatos.GestorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements FuncionesObligatorias<Usuario> {
    private int user_id;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private Connection conexion = GestorBD.getConexion();

    public Usuario() {}

    public Usuario(int user_id, String nombre, String apellido, String email, String contrasena) {
        this.user_id = user_id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasena = contrasena;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public boolean create(Usuario usuario) {
        try {
            String consulta = "INSERT INTO Usuarios (nombre, apellido, email, contrasena) VALUES (?, ?, ?, ?)";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setString(1, usuario.getNombre());
            consultaE.setString(2, usuario.getApellido());
            consultaE.setString(3, usuario.getEmail());
            consultaE.setString(4, usuario.getContrasena());
            consultaE.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario read(int user_id) {
        try {
            String consulta = "SELECT * FROM Usuarios WHERE user_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, user_id);
            ResultSet resultado = consultaE.executeQuery();
            if (resultado.next()) {
                return new Usuario(
                        resultado.getInt("user_id"),
                        resultado.getString("nombre"),
                        resultado.getString("apellido"),
                        resultado.getString("email"),
                        resultado.getString("contrasena")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Usuario usuario) {
        try {
            String consulta = "UPDATE Usuarios SET nombre = ?, apellido = ?, email = ?, contrasena = ? WHERE user_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setString(1, usuario.getNombre());
            consultaE.setString(2, usuario.getApellido());
            consultaE.setString(3, usuario.getEmail());
            consultaE.setString(4, usuario.getContrasena());
            consultaE.setInt(5, usuario.getUserId());
            int filasAfectadas = consultaE.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int user_id) {
        try {
            String consulta = "DELETE FROM Usuarios WHERE user_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, user_id);
            int filasAfectadas = consultaE.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Usuario> readAll() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM Usuarios";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            ResultSet resultado = consultaE.executeQuery();
            while (resultado.next()) {
                usuarios.add(new Usuario(
                        resultado.getInt("user_id"),
                        resultado.getString("nombre"),
                        resultado.getString("apellido"),
                        resultado.getString("email"),
                        resultado.getString("contrasena")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public boolean verificarCredenciales(String email, String contrasena) {
        try {
            String consulta = "SELECT * FROM Usuarios WHERE email = ? AND contrasena = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setString(1, email);
            consultaE.setString(2, contrasena);
            ResultSet resultado = consultaE.executeQuery();
            return resultado.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int obtenerUserId(String email) {
        try {
            String consulta = "SELECT user_id FROM Usuarios WHERE email = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setString(1, email);
            ResultSet resultado = consultaE.executeQuery();
            if (resultado.next()) {
                return resultado.getInt("user_id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
