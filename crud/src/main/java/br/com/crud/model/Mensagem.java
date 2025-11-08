package br.com.crud.model;

import lombok.Data;  
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class Mensagem {

    private int id; 
    private String texto; 

}