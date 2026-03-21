package app;

import static spark.Spark.*;

import service.CarrosService;

public class Aplicacao {
	
	private static CarrosService carrosService = new CarrosService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/carros/insert", (request, response) -> carrosService.insert(request, response));

        get("/carros/:id", (request, response) -> carrosService.get(request, response));
        
        get("/carros/list/:orderby", (request, response) -> carrosService.getAll(request, response));

        get("/carros/update/:id", (request, response) -> carrosService.getToUpdate(request, response));
        
        post("/carros/update/:id", (request, response) -> carrosService.update(request, response));
           
        get("/carros/delete/:id", (request, response) -> carrosService.delete(request, response));

        System.out.println("Servidor rodando...");     
    }
}