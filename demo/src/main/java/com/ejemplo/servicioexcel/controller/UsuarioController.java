package com.ejemplo.servicioexcel.controller;
import com.ejemplo.servicioexcel.entity.Usuario;
import com.ejemplo.servicioexcel.repository.UsuarioRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/cargar")
    public String cargarArchivoExcel(@RequestParam("file") MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar encabezado

                Usuario usuario = new Usuario();
                usuario.setNombre(row.getCell(0).getStringCellValue());
                usuario.setRut(row.getCell(1).getStringCellValue());
                usuario.setCampo1(row.getCell(2).getStringCellValue());
                usuario.setCampo2(row.getCell(3).getStringCellValue());

                usuarioRepository.save(usuario);
            }
            return "Carga de datos exitosa!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al cargar el archivo!";
        }
    }

    @GetMapping("/consultar")
    public List<Usuario> consultarUsuarios() {
        return usuarioRepository.findAll();
    }
}
