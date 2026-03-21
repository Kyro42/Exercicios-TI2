package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import model.Carros;
import spark.Request;
import spark.Response;
import dao.CarrosDAO;

public class CarrosService {

	private CarrosDAO CarrosDAO = new CarrosDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_MARCA = 3;
	private final int FORM_ORDERBY_VALOR = 4;
	
	
	public CarrosService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Carros(), FORM_ORDERBY_ID);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Carros(), orderBy);
	}

	
	public void makeForm(int tipo, Carros carro, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umCarro = "";
		if(tipo != FORM_INSERT) {
			umCarro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/carros/list/1\">Novo Carro</a></b></font></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t</table>";
			umCarro += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/carros/";
			String nome, marca, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				nome = "Inserir Carro";
				marca = "Mitsubishi, BYD, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + carro.getId();
				nome = "Atualizar Carro (ID " + carro.getId() + ")";
				marca = carro.getMarca();
				buttonLabel = "Atualizar";
			}
			umCarro += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umCarro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + nome + "</b></font></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td>Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ carro.getNome() +"\"></td>";
			umCarro += "\t\t\t<td>Marca: <input class=\"input--register\" type=\"text\" name=\"marca\" value=\""+ carro.getMarca() +"\"></td>";
			umCarro += "\t\t\t<td>Valor: <input class=\"input--register\" type=\"text\" name=\"valor\"valor=\"valor\" value=\""+ carro.getValor() +"\"></td>";
			umCarro += "\t\t\t<td>Eletrico: <input class=\"input--register\" type=\"text\" name=\"eletrico\"valor=\"valor\" value=\""+ carro.getEletrico() +"\"></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t</table>";
			umCarro += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umCarro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Carro (ID " + carro.getId() + ")</b></font></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td>&nbsp;Nome: "+ carro.getNome() +"</td>";
			umCarro += "\t\t\t<td>Marca: "+ carro.getMarca() +"</td>";
			umCarro += "\t\t\t<td>Valor: "+ carro.getValor() +"</td>";
			umCarro += "\t\t\t<td>Eletrico: "+ carro.getEletrico() +"</td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t\t<td>&nbsp;</td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-CARRO>", umCarro);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Carros</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/carros/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/carros/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/carros/list/" + FORM_ORDERBY_MARCA + "\"><b>Marca</b></a></td>\n" +
        		"\t<td><a href=\"/carros/list/" + FORM_ORDERBY_VALOR + "\"><b>Valor</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Carros> carros;
		if (orderBy == FORM_ORDERBY_ID) {                 	carros = CarrosDAO.getOrderById();
		} else if (orderBy == FORM_ORDERBY_NOME) {		carros = CarrosDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_MARCA) {			carros = CarrosDAO.getOrderByMarca();
		} else if (orderBy == FORM_ORDERBY_VALOR){			carros = CarrosDAO.getOrderByValor();
		}else {											carros = CarrosDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Carros p : carros) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getId() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getMarca() + "</td>\n" +
            		  "\t<td>" + p.getValor() + "</td>\n" +
            		  "\t<td>" + p.getEletrico() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/carros/" + p.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/carros/update/" + p.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"#\" onclick=\"confirmarDeleteCarro('"
            		  + p.getId() + "', '" + p.getNome() + "', '" + p.getMarca() + "', '" + p.getValor() + "', '" + p.getEletrico() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" + "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-CARROS>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		String marca = request.queryParams("marca");
		Double valor = Double.parseDouble(request.queryParams("valor"));
		Boolean eletrico = Boolean.parseBoolean(request.queryParams("eletrico"));
		
		String resp = "";
		
		Carros carro = new Carros(-1, nome, marca, valor, eletrico);
		
		if(CarrosDAO.insert(carro) == true) {
            resp = "Carro (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Carro (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Carros carro = (Carros) CarrosDAO.get(id);
		
		if (carro != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, carro, FORM_ORDERBY_ID);
        } else {
            response.status(404); // 404 Not found
            String resp = "carro " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Carros carro = (Carros) CarrosDAO.get(id);
		
		if (carro != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, carro, FORM_ORDERBY_ID);
        } else {
            response.status(404); // 404 Not found
            String resp = "Carro " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Carros carro = CarrosDAO.get(id);
        String resp = "";       

        if (carro != null) {
        	carro.setNome(request.queryParams("nome"));
        	carro.setMarca(request.queryParams("marca"));
        	carro.setValor(Double.parseDouble(request.queryParams("valor")));
        	carro.setEletrico(Boolean.parseBoolean(request.queryParams("eletrico")));
        	CarrosDAO.update(carro);
        	response.status(200); // success
            resp = "Carro (ID " + carro.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Carro (ID " + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Carros carro = CarrosDAO.get(id);
        String resp = "";       

        if (carro != null) {
        	CarrosDAO.delete(id);
            response.status(200); // success
            resp = "Carro (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Carro (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}