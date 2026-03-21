package dao;
import model.Carros;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarrosDAO extends DAO{
	public CarrosDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Carros carro) {
		boolean status = false;
		try {
			String sql = "INSERT INTO carros(nome, marca, valor, eletrico) VALUES (?, ?, ?, ?)";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, carro.getNome());
			st.setString(2, carro.getMarca());
			st.setDouble(3, carro.getValor());
			st.setBoolean(4, carro.getEletrico());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Carros get(int id) {
		Carros carro = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM carros WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 carro = new Carros(rs.getInt("id"), rs.getString("nome"), rs.getString("marca"), rs.getDouble("valor"), rs.getBoolean("eletrico"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return carro;
	}
	
	
	public List<Carros> get() {
		return get("");
	}

	
	public List<Carros> getOrderById() {
		return get("id");		
	}
	
	
	public List<Carros> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Carros> getOrderByMarca() {
		return get("marca");		
	}
	
	public List<Carros> getOrderByValor() {
		return get("valor");		
	}
	
	public List<Carros> getOrderByEletrico() {
		return get("eletrico");		
	}
	
	private List<Carros> get(String orderBy) {
		List<Carros> carro = new ArrayList<Carros>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM carros" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Carros p = new Carros(rs.getInt("id"), rs.getString("nome"), rs.getString("marca"), rs.getDouble("valor"), rs.getBoolean("eletrico"));
	            carro.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return carro;
	}
	
	
	public boolean update(Carros carro) {
		boolean status = false;
		try {  
			String sql = "UPDATE carros SET nome=?, marca=?, valor=?, eletrico=? WHERE id=?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, carro.getNome());
			st.setString(2, carro.getMarca());
			st.setDouble(3, carro.getValor());
			st.setBoolean(4, carro.getEletrico());
			st.setInt(5, carro.getId());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM carros WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

}
