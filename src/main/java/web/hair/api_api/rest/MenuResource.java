package web.hair.api_api.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.hair.api_api.model.MenuDTO;
import web.hair.api_api.service.MenuService;


@RestController
@RequestMapping(value = "/api/menus", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuResource {

    private final MenuService menuService;

    public MenuResource(final MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAllMenus() {
        return ResponseEntity.ok(menuService.findAll());
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuDTO> getMenu(@PathVariable(name = "menuId") final Long menuId) {
        return ResponseEntity.ok(menuService.get(menuId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMenu(@RequestBody @Valid final MenuDTO menuDTO) {
        final Long createdMenuId = menuService.create(menuDTO);
        return new ResponseEntity<>(createdMenuId, HttpStatus.CREATED);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<Long> updateMenu(@PathVariable(name = "menuId") final Long menuId,
            @RequestBody @Valid final MenuDTO menuDTO) {
        menuService.update(menuId, menuDTO);
        return ResponseEntity.ok(menuId);
    }

    @DeleteMapping("/{menuId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMenu(@PathVariable(name = "menuId") final Long menuId) {
        menuService.delete(menuId);
        return ResponseEntity.noContent().build();
    }

}
