package io.t3w.app.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import io.t3w.app.entities.T3WUsuarioEntity;
import io.t3w.app.services.T3WUsuarioService;

@Route("")
public class T3WUsuarioView extends Div {

    public T3WUsuarioView(T3WUsuarioService usuarioService) {

        final var binder = new Binder<T3WUsuarioEntity>();
        binder.setBean(new T3WUsuarioEntity());

        final var button = new Button();

        final var tfFind = new TextField("Search term:");
        tfFind.setValue("admin");

        button.setText("Find");
        button.addClickListener(e -> {
            final var usuario = usuarioService.findUsuarioByUsername(tfFind.getValue());
            if (usuario == null) {
                final Notification notification = new Notification();
                notification.setPosition(Notification.Position.TOP_START);
                notification.setDuration(1000);
                notification.add("Nada encontrado");
                notification.open();
                binder.setBean(new T3WUsuarioEntity());
            } else {
                binder.setBean(usuario);
            }
        });

        final var tfId = new TextField("id");
        final var tfName = new TextField("nome");
        final var tfEmail = new TextField("email");
        final var tfSenha = new PasswordField("senha");

        binder.bindReadOnly(tfId, u -> u.getId() != null ? u.getId().toString() : "");
        binder.bind(tfName, T3WUsuarioEntity::getNome, T3WUsuarioEntity::setNome);
        binder.bind(tfEmail, T3WUsuarioEntity::getEmail, T3WUsuarioEntity::setEmail);
        binder.forField(tfSenha).bind(_ -> "", T3WUsuarioEntity::setSenha);

        final var buttonSave = new Button("Salvar");

        buttonSave.addClickListener(ev -> {
            try {
                binder.writeBean(binder.getBean());
                usuarioService.saveUsuario(binder.getBean());
            } catch (Exception e) {
                final Notification notification = new Notification();
                notification.setPosition(Notification.Position.TOP_START);
                notification.setDuration(1000);
                notification.add("Erro ao salvar");
                notification.add(e.getMessage());
                notification.open();
                throw new RuntimeException(e);
            }
        });

        this.add(tfFind);
        this.add(button);
        this.add(new Hr());
        this.add(tfId, tfName, tfEmail, tfSenha);
        this.add(buttonSave);


        //grid
        final var grid = new com.vaadin.flow.component.grid.Grid<T3WUsuarioEntity>();
        grid.setItemsPageable(usuarioService::listPageable);
        //grid.setDataProvider(VaadinSpringDataHelpers.fromPagingRepository(usuarioRepository));
        grid.addColumn(T3WUsuarioEntity::getId).setHeader("ID");
        grid.addColumn(T3WUsuarioEntity::getNome).setHeader("Nome");
        grid.addColumn(T3WUsuarioEntity::getEmail).setHeader("Email");
        this.add(grid);
    }
}
