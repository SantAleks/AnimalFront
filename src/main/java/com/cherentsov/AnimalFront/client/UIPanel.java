package com.cherentsov.AnimalFront.client;

import com.cherentsov.AnimalFront.shared.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class UIPanel extends Composite {

    @UiField HorizontalPanel topHorizontalPanel;
    @UiField VerticalPanel topVerticalPanelComand;
    @UiField VerticalPanel srchVerticalPanelPoint;
    @UiField Button bViewAll;
    @UiField SuggestBox sbTakeOne;
    @UiField Button bTakeOne;
    @UiField SuggestBox sbRegion;
    @UiField SuggestBox sbColor;
    @UiField SuggestBox sbType;
    @UiField Button bSearch;
    @UiField SuggestBox sbDelete;
    @UiField Button bDelete;
    @UiField Grid gPet;
    @UiField MyStyle style;
    @UiField SuggestBox sbcName;
    @UiField ListBox lbcAnimalType;
    @UiField ListBox lbcSkinColor;
    @UiField ListBox lbcLocation;
    @UiField Button bCreate;

    @UiField SuggestBox sbuId;
    @UiField SuggestBox sbuName;
    @UiField ListBox lbuAnimalType;
    @UiField ListBox lbuSkinColor;
    @UiField ListBox lbuLocation;
    @UiField Button bUpdate;
    //@UiField HTMLPanel mapPanel;

    private int selectIndexGrid = -1;
    private List<AnimalType> lsAnimalType = new ArrayList<AnimalType>();
    private List<Location> lsLocation = new ArrayList<Location>();
    private List<SkinColor> lsSkinColor = new ArrayList<SkinColor>();
    private List<Pet> lsPet = new ArrayList<Pet>();

    private final GwtServiceIntf gwtAppService = GWT.create(GwtServiceIntf.class);

    interface UIPanelUiBinder extends UiBinder<HTMLPanel, UIPanel> {
    }

    interface MyStyle extends CssResource {

        String suggestBox();

        @ClassName("tableCell-even")
        String tableCellEven();

        String label();

        @ClassName("tableCell-odd")
        String tableCellOdd();

        @ClassName("tableCell-cap")
        String tableCellCap();

        @ClassName("tableCell-select")
        String tableCellSelect();

        String button();

        String labelBold();
    }

    private static UIPanelUiBinder ourUiBinder = GWT.create(UIPanelUiBinder.class);

    public UIPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));

        /*bSearch.addClickHandler(event -> {
            if (uIContent.getCountry() != sbCountry.getValue()){
                uIContent.setCountry(sbCountry.getValue());
                sendInfoToServer("Country");
            }
        });
*/
        initListBox();

        bViewAll.addClickHandler(event -> {
                reciveAll();
        });

        bTakeOne.addClickHandler(event -> {
            reciveOne();
        });

        bSearch.addClickHandler(event -> {
            search();
        });

        bDelete.addClickHandler(event -> {
            delete();
        });

        bCreate.addClickHandler(event -> {
            create();
        });

        bUpdate.addClickHandler(event -> {
            update();
        });

        //выбор записи животного мышкой для update
        gPet.addClickHandler(event -> {
            selectIndexGrid = gPet.getCellForEvent(event).getRowIndex();
            if (selectIndexGrid >=1){
                Pet pet =  lsPet.get(selectIndexGrid-1);
                sbuId.setText(pet.getId().toString());
                sbuName.setText(pet.getName());
                lbuLocation.setSelectedIndex(lsLocation.indexOf(pet.getLocation()));
                lbuAnimalType.setSelectedIndex(lsAnimalType.indexOf(pet.getAnimalType()));
                lbuSkinColor.setSelectedIndex(lsSkinColor.indexOf(pet.getSkinColor()));
            }
        });
    }

    private void reciveAll() {

        gwtAppService.gwtCallServer("", new MethodCallback<List<Pet>>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {
                Window.alert("Ошибка. Сервер предоставления данных справочника не работает.");
            }

            @Override
            public void onSuccess(final Method method, final List<Pet> result) {
                selectIndexGrid = -1;
                drawGrid(result);
            }
        });
    }

    private void reciveOne() {
        Long lId = 0L;
        try {
            lId = Long.parseLong(sbTakeOne.getValue());
        }
        catch (Exception e){
            Window.alert("Ошибка. Направильный код записи. " + e.toString());
            return;
        }
        gwtAppService.gwtCallServerTakeId(lId, new MethodCallback<List<Pet>>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {
                Window.alert("Ошибка. Сервер предоставления данных справочника не работает.");
            }

            @Override
            public void onSuccess(final Method method, final List<Pet> result) {
                selectIndexGrid = -1;
                drawGrid(result);
            }
        });
    }

    private void search() {
        StringBuilder sSearch = new StringBuilder();
        //Чистим входные данные от возможных инъекций и формируем запрос к Бэкэнду
        sSearch.append("?region=").append(sbRegion.getValue().replaceAll("\\p{P}|\\p{S}", ""))
                .append("&color=").append(sbColor.getValue().replaceAll("\\p{P}|\\p{S}", ""))
                .append("&type=").append(sbType.getValue().replaceAll("\\p{P}|\\p{S}", ""));

        gwtAppService.gwtCallServerSearch(sSearch.toString(), new MethodCallback<List<Pet>>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {
                Window.alert("Ошибка. Сервер предоставления данных справочника не работает.");
            }

            @Override
            public void onSuccess(final Method method, final List<Pet> result) {
                selectIndexGrid = -1;
                drawGrid(result);
            }
        });
    }

    private void delete() {
        Long lId = 0L;
        try {
            lId = Long.parseLong(sbDelete.getValue());
        }
        catch (Exception e){
            Window.alert("Ошибка. Направильный код записи. " + e.toString());
            return;
        }
        gwtAppService.gwtCallServerDelete(lId, new MethodCallback<Void>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {
                org.fusesource.restygwt.client.FailedResponseException ddd;
                if (exception.getClass().getName().equals("org.fusesource.restygwt.client.FailedResponseException")){
                    Window.alert("Ошибка. Неправильный код животного");
                }
                else {
                    Window.alert("Ошибка. Сервер предоставления данных справочника не работает.");
                }
            }
            @Override
            public void onSuccess(final Method method, final Void result) {
                Window.alert("Запись о животном удалена");
            }
        });
    }

    private void create() {
        Pet pet = new Pet(-1L, sbcName.getText(), lsLocation.get(lbcLocation.getSelectedIndex()),
                lsAnimalType.get(lbcAnimalType.getSelectedIndex()),
                lsSkinColor.get(lbcSkinColor.getSelectedIndex()));

        gwtAppService.gwtCallServerCreate(pet, new MethodCallback<Void>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {
                if (exception.getClass().getName().equals("org.fusesource.restygwt.client.FailedResponseException")){
                    Window.alert("Ошибка. Создать животного не удалось");
                }
                else {
                    Window.alert("Ошибка. Сервер предоставления данных справочника не работает.");
                }
            }
            @Override
            public void onSuccess(final Method method, final Void result) {
                Window.alert("Запись о животном создана");
            }
        });
    }

    private void update() {
        Long lId = 0L;
        try {
            lId = Long.parseLong(sbuId.getValue());
        }
        catch (Exception e){
            Window.alert("Ошибка. Направильный код записи. " + e.toString());
            return;
        }
        Pet pet = new Pet(lId, sbuName.getText(), lsLocation.get(lbuLocation.getSelectedIndex()),
                lsAnimalType.get(lbuAnimalType.getSelectedIndex()),
                lsSkinColor.get(lbuSkinColor.getSelectedIndex()));

        gwtAppService.gwtCallServerUpdate(pet, new MethodCallback<Void>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {
                if (exception.getClass().getName().equals("org.fusesource.restygwt.client.FailedResponseException")){
                    Window.alert("Ошибка. Обновить животного не удалось");
                }
                else {
                    Window.alert(exception.getClass().getName());
                    Window.alert("Ошибка. Сервер предоставления данных справочника не работает.");
                }
            }
            @Override
            public void onSuccess(final Method method, final Void result) {
                Window.alert("Запись о животном обновлена");
            }
        });
    }

    private void drawGrid(List<Pet> lPet){
        lsPet = lPet;
        gPet.clear();
        if (lPet.size()>0){
            gPet.resize(lPet.size()+1, 6);
            gPet.setText(0, 0 , "Id");
            gPet.setText(0, 1 , "Наименование");
            gPet.setText(0, 2 , "Местоположение");
            gPet.setText(0, 3 , "Тип животного");
            gPet.setText(0, 4 , "Цвет шкуры");
            gPet.setText(0, 5 , "Регион");
            gPet.getRowFormatter().setStyleName(0,style.tableCellCap());
            for (int i = 0; i < lPet.size(); i++){
                Pet petEntity = lPet.get(i);
                gPet.setText(i+1, 0 , petEntity.getId().toString());
                gPet.setText(i+1, 1 , petEntity.getName());
                gPet.setText(i+1, 2 , petEntity.getLocation().getName());
                gPet.setText(i+1, 3 , petEntity.getAnimalType().getName());
                gPet.setText(i+1, 4 , petEntity.getSkinColor().getName());
                gPet.setText(i+1, 5 , petEntity.getLocation().getRegion().getName());
                if ((i % 2) == 0) {
                    gPet.getRowFormatter().setStyleName(i+1,style.tableCellEven());
                } else {
                    gPet.getRowFormatter().setStyleName(i+1,  style.tableCellOdd());
                }

                if (i+1 == selectIndexGrid) {
                    gPet.getRowFormatter().addStyleName(i+1,style.tableCellSelect());
                }
            }
        }
        else {
            gPet.resize(0,0);
        }
    }

    //Процедура заполнения листбоксов параметров животных для операций создания и изменения записей
    private void initListBox() {
        gwtAppService.gwtCallServerAnimalType("", new MethodCallback<List<AnimalType>>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {
                Window.alert("Ошибка. Сервер предоставления данных справочника не работает.");
            }

            @Override
            public void onSuccess(final Method method, final List<AnimalType> result1) {
                for(AnimalType i : result1){
                    lbcAnimalType.addItem(i.getName());
                    lbuAnimalType.addItem(i.getName());
                    lsAnimalType.add(i);
                }
            }
        });

        gwtAppService.gwtCallServerLocation("", new MethodCallback<List<Location>>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {
                Window.alert("Ошибка. Сервер предоставления данных справочника не работает.");
            }

            @Override
            public void onSuccess(final Method method, final List<Location> result) {
                for(Location i : result){
                    lbcLocation.addItem(i.getName());
                    lbuLocation.addItem(i.getName());
                    lsLocation.add(i);
                }
            }
        });

        gwtAppService.gwtCallServerSkinColor("", new MethodCallback<List<SkinColor>>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {
                Window.alert("Ошибка. Сервер предоставления данных справочника не работает.");
            }

            @Override
            public void onSuccess(final Method method, final List<SkinColor> result) {
                for(SkinColor i : result){
                    lbcSkinColor.addItem(i.getName());
                    lbuSkinColor.addItem(i.getName());
                    lsSkinColor.add(i);
                }
            }
        });


    }
}

