package web.hair.api_api.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.hair.api_api.domain.Menu;
import web.hair.api_api.model.MenuDTO;
import web.hair.api_api.repos.MenuRepository;
import web.hair.api_api.util.NotFoundException;


@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuDTO> findAll() {
        final List<Menu> menus = menuRepository.findAll(Sort.by("menuId"));
        return menus.stream()
                .map(menu -> mapToDTO(menu, new MenuDTO()))
                .toList();
    }

    public MenuDTO get(final Long menuId) {
        return menuRepository.findById(menuId)
                .map(menu -> mapToDTO(menu, new MenuDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MenuDTO menuDTO) {
        final Menu menu = new Menu();
        mapToEntity(menuDTO, menu);
        return menuRepository.save(menu).getMenuId();
    }

    public void update(final Long menuId, final MenuDTO menuDTO) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(menuDTO, menu);
        menuRepository.save(menu);
    }

    public void delete(final Long menuId) {
        menuRepository.deleteById(menuId);
    }

    private MenuDTO mapToDTO(final Menu menu, final MenuDTO menuDTO) {
        menuDTO.setMenuId(menu.getMenuId());
        menuDTO.setName(menu.getName());
        menuDTO.setPrice(menu.getPrice());
        return menuDTO;
    }

    private Menu mapToEntity(final MenuDTO menuDTO, final Menu menu) {
        menu.setName(menuDTO.getName());
        menu.setPrice(menuDTO.getPrice());
        return menu;
    }

}
