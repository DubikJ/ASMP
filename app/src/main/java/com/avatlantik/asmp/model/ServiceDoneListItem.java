package com.avatlantik.asmp.model;

import com.avatlantik.asmp.model.db.Animal;
import com.avatlantik.asmp.model.db.AnimalDispos;
import com.avatlantik.asmp.model.db.AnimalStatus;
import com.avatlantik.asmp.model.db.Housing;
import com.avatlantik.asmp.model.db.ServiceData;
import com.avatlantik.asmp.model.db.ServiceDone;
import com.avatlantik.asmp.model.db.ServiceDoneVetExercise;

import java.util.Date;
import java.util.List;

public class ServiceDoneListItem extends ServiceDone{


    private Animal boar1A;
    private Animal boar2A;
    private Animal boar3A;
    private String housing;
    private Animal animal;
    private Animal group;
    private Animal tecnGroupToA;
    private Housing corpToH;
    private Housing sectorToH;
    private Housing cellToH;
    private ServiceData serviceData;
    private ServiceData resultService;
    private AnimalStatus statusA;
    private AnimalDispos disposAnimA;
    private Boolean changed;
    private List<ServiceDoneVetExercise> vetExerciseList;
    private Animal animalGroupToA;

    public ServiceDoneListItem(int id, Date date, Date dateDay, String animalId, String serviceId,
                               Boolean isPlane, Boolean done, String note, int number,
                               int live, int normal, int weak, int death, int mummy, double weight,
                               String boar1, String boar2, String boar3, String tecnGroupTo,
                               String corpTo, String sectorTo, String cellTo, String resultService,
                               int admNumber, String status, String disposAnim,
                               int length, int bread, int exterior, int depthMysz, String newCode,
                               boolean artifInsemen, String animalGroupTo) {

        super(id, date, dateDay, animalId, serviceId, isPlane, done, note, number,
                live, normal, weak, death, mummy, weight,
                boar1, boar2, boar3, tecnGroupTo,
                corpTo, sectorTo, cellTo, resultService, admNumber,
                status, disposAnim, length, bread, exterior, depthMysz, newCode, artifInsemen, animalGroupTo);
    }

    public Animal getBoar1A() {
        return boar1A;
    }

    public void setBoar1A(Animal boar1A) {
        this.boar1A = boar1A;
    }

    public Animal getBoar2A() {
        return boar2A;
    }

    public void setBoar2A(Animal boar2A) {
        this.boar2A = boar2A;
    }

    public Animal getBoar3A() {
        return boar3A;
    }

    public void setBoar3A(Animal boar3A) {
        this.boar3A = boar3A;
    }

    public String getHousing() {
        return housing;
    }

    public void setHousing(String housing) {
        this.housing = housing;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Animal getGroup() {
        return group;
    }

    public void setGroup(Animal group) {
        this.group = group;
    }

    public Animal getTecnGroupToA() {
        return tecnGroupToA;
    }

    public void setTecnGroupToA(Animal tecnGroupToA) {
        this.tecnGroupToA = tecnGroupToA;
    }

    public Housing getCorpToH() {
        return corpToH;
    }

    public void setCorpToH(Housing corpToH) {
        this.corpToH = corpToH;
    }

    public Housing getSectorToH() {
        return sectorToH;
    }

    public void setSectorToH(Housing sectorToH) {
        this.sectorToH = sectorToH;
    }

    public Housing getCellToH() {
        return cellToH;
    }

    public void setCellToH(Housing cellToH) {
        this.cellToH = cellToH;
    }

    public ServiceData getServiceData() {
        return serviceData;
    }

    public void setServiceData(ServiceData serviceData) {
        this.serviceData = serviceData;
    }

    public ServiceData getResultServiceS() {
        return resultService;
    }

    public void setResultService(ServiceData resultService) {
        this.resultService = resultService;
    }

    public AnimalStatus getStatusA() {
        return statusA;
    }

    public void setStatusA(AnimalStatus statusA) {
        this.statusA = statusA;
    }

    public AnimalDispos getDisposAnimA() {
        return disposAnimA;
    }

    public void setDisposAnimA(AnimalDispos disposAnimA) {
        this.disposAnimA = disposAnimA;
    }

    public Boolean getChanged() {
        return changed;
    }

    public void setChanged(Boolean changed) {
        this.changed = changed;
    }

    public List<ServiceDoneVetExercise> getVetExerciseList() {
        return vetExerciseList;
    }

    public void setVetExerciseList(List<ServiceDoneVetExercise> vetExerciseList) {
        this.vetExerciseList = vetExerciseList;
    }

    public Animal getAnimalGroupToA() {return animalGroupToA;}

    public void setAnimalGroupToA(Animal animalGroupToA) {this.animalGroupToA = animalGroupToA;}
}
