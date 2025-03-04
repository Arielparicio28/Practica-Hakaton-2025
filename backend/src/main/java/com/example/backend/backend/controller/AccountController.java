
package com.example.backend.backend.controller;

import com.example.backend.backend.dto.AccountDTO;
import com.example.backend.backend.dto.ApiResponse;
import com.example.backend.backend.dto.UpdateAccountDTO;
import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.model.AccountModel;
import com.example.backend.backend.model.UsersModel;
import com.example.backend.backend.services.AccountService;
import com.example.backend.backend.services.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<ApiResponse> getAllAccounts()
    {
        List<AccountModel> accounts = accountService.getAllAccount();
        ApiResponse response = new ApiResponse("Datos de todas las cuentas",accounts);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    // Crear una cuenta
    @PostMapping
    public ResponseEntity<ApiResponse> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        AccountModel account = accountService.createAccount(accountDTO);
        ApiResponse response = new ApiResponse("Cuenta creada con éxito", account);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Nuevo endpoint: obtener el usuario asociado a una cuenta
    @GetMapping("/{accountId}/user")
    public ResponseEntity<ApiResponse> getUserForAccount(@PathVariable String accountId) {
        // Obtener la cuenta asociada
        AccountModel account = accountService.getUserByAccountId(accountId);

        if (account == null) {
            return ResponseEntity.notFound().build(); // Si la cuenta no existe, devolver 404
        }

        // Obtener el ObjectId del usuario desde la cuenta
        ObjectId userId = account.getUserId();

        // Buscar el usuario en la base de datos
        UserDTO userDto = userService.getUserById(userId.toHexString());

        ApiResponse response = new ApiResponse("Usuario obtenido correctamente", userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Obtener una cuenta por su id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAccountId(@PathVariable String id)
    {
        AccountModel account =  accountService.getAccountById(id);
        ApiResponse response = new ApiResponse("Cuenta obtenida correctamente",account);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //Eliminar una cuenta por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable String id){
        AccountModel account = accountService.deleteAccount(id);
        ApiResponse response = new ApiResponse("Cuenta eliminada con éxito", account);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


  // Actualizar cuenta
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateAccount(@PathVariable String id,@Valid @RequestBody UpdateAccountDTO updateAccountDTO)
    {
        AccountModel updatedAccount = accountService.updateAccount(id,updateAccountDTO);
        ApiResponse response = new ApiResponse("Cuenta actualizada con exito",updatedAccount);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
