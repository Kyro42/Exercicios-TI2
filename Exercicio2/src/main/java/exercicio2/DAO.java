package exercicio2;

import java.sql.*;

public class DAO {
	private Connection conexao;
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
		String username = "kairo";
		String password = "123123";
		boolean status = false;
		
		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		try {
			conexao.close();
			status = true;
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean inserirCarro(Carros carro) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO carros(id, nome, marca, valor, eletrico) " + "VALUES (" + carro.getId() + ", '" + carro.getNome() + "', '" + carro.getMarca() + "', " + carro.getValor() + ", " + carro.getEletrico() + ");");
			st.close();
			status = true;
		}catch(SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarCarro(Carros carro) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE carros SET id = " + carro.getId() + ", nome = '" + carro.getNome() + "', marca = '" + carro.getMarca() + "', valor = " + carro.getValor() + ", eletrico = " + carro.getEletrico();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch(SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirCarro(int id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM carros WHERE id = " + id);
			st.close();
			status = true;
		} catch(SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Carros[] getCarros() {
		Carros[] carro = new Carros[0];
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM carros");
			if(rs.next()){
	             rs.last();
	             carro = new Carros[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                carro[i] = new Carros(rs.getInt("id"), rs.getString("nome"), rs.getString("marca"), rs.getDouble("valor"), rs.getBoolean("eletrico"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return carro;
	}
}
