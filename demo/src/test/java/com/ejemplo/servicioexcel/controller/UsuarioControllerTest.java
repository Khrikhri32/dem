package com.ejemplo.servicioexcel.controller;

import com.ejemplo.servicioexcel.entity.Usuario;
import com.ejemplo.servicioexcel.repository.UsuarioRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UsuarioControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCargarArchivoExcel() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Nombre");
        header.createCell(1).setCellValue("Rut");
        header.createCell(2).setCellValue("Campo1");
        header.createCell(3).setCellValue("Campo2");

        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("Juan Perez");
        row1.createCell(1).setCellValue("12345678-9");
        row1.createCell(2).setCellValue("info1");
        row1.createCell(3).setCellValue("info2");

        Row row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("Maria Lopez");
        row2.createCell(1).setCellValue("98765432-1");
        row2.createCell(2).setCellValue("info3");
        row2.createCell(3).setCellValue("info4");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        byte[] excelBytes = bos.toByteArray();

        MockMultipartFile file = new MockMultipartFile("file", "usuarios.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", excelBytes);

        String resultado = usuarioController.cargarArchivoExcel(file);
        assertEquals("Carga de datos exitosa!", resultado);

        verify(usuarioRepository, times(2)).save(any(Usuario.class));
    }
}
