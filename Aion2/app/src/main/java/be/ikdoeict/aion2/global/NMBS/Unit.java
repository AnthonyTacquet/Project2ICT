package be.ikdoeict.aion2.global.NMBS;

public class Unit {
    private int id;
    private Material material;
    private boolean hasToilets;
    private boolean hasTables;
    private boolean hasSecondClassOutlets;
    private boolean hasFirstClassOutlets;
    private boolean hasHeating;
    private boolean hasAirco;
    private String materialNumber;
    private String tractionType;
    private boolean canPassToNextUnit;
    private int standingPlacesSecondClass;
    private int standingPlacesFirstClass;
    private int seatsCoupeSecondClass;
    private int seatsCoupeFirstClass;
    private int seatsSecondClass;
    private int seatsFirstClass;
    private int lengthInMeter;
    private boolean hasSemiAutomaticInteriorDoors;
    private boolean hasLuggageSection;
    private String materialSubTypeName;
    private int tractionPosition;
    private boolean hasPrmSection; // passenger with reduced mobility
    private boolean hasPriorityPlaces;
    private boolean hasBikeSection;

    public Unit(int id, Material material, boolean hasToilets, boolean hasTables, boolean hasSecondClassOutlets, boolean hasFirstClassOutlets, boolean hasHeating, boolean hasAirco, String materialNumber, String tractionType, boolean canPassToNextUnit, int standingPlacesSecondClass, int standingPlacesFirstClass, int seatsCoupeSecondClass, int seatsCoupeFirstClass, int seatsSecondClass, int seatsFirstClass, int lengthInMeter, boolean hasSemiAutomaticInteriorDoors, boolean hasLuggageSection, String materialSubTypeName, int tractionPosition, boolean hasPrmSection, boolean hasPriorityPlaces, boolean hasBikeSection) {
        this.id = id;
        this.material = material;
        this.hasToilets = hasToilets;
        this.hasTables = hasTables;
        this.hasSecondClassOutlets = hasSecondClassOutlets;
        this.hasFirstClassOutlets = hasFirstClassOutlets;
        this.hasHeating = hasHeating;
        this.hasAirco = hasAirco;
        this.materialNumber = materialNumber;
        this.tractionType = tractionType;
        this.canPassToNextUnit = canPassToNextUnit;
        this.standingPlacesSecondClass = standingPlacesSecondClass;
        this.standingPlacesFirstClass = standingPlacesFirstClass;
        this.seatsCoupeSecondClass = seatsCoupeSecondClass;
        this.seatsCoupeFirstClass = seatsCoupeFirstClass;
        this.seatsSecondClass = seatsSecondClass;
        this.seatsFirstClass = seatsFirstClass;
        this.lengthInMeter = lengthInMeter;
        this.hasSemiAutomaticInteriorDoors = hasSemiAutomaticInteriorDoors;
        this.hasLuggageSection = hasLuggageSection;
        this.materialSubTypeName = materialSubTypeName;
        this.tractionPosition = tractionPosition;
        this.hasPrmSection = hasPrmSection;
        this.hasPriorityPlaces = hasPriorityPlaces;
        this.hasBikeSection = hasBikeSection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public boolean isHasToilets() {
        return hasToilets;
    }

    public void setHasToilets(boolean hasToilets) {
        this.hasToilets = hasToilets;
    }

    public boolean isHasTables() {
        return hasTables;
    }

    public void setHasTables(boolean hasTables) {
        this.hasTables = hasTables;
    }

    public boolean isHasSecondClassOutlets() {
        return hasSecondClassOutlets;
    }

    public void setHasSecondClassOutlets(boolean hasSecondClassOutlets) {
        this.hasSecondClassOutlets = hasSecondClassOutlets;
    }

    public boolean isHasFirstClassOutlets() {
        return hasFirstClassOutlets;
    }

    public void setHasFirstClassOutlets(boolean hasFirstClassOutlets) {
        this.hasFirstClassOutlets = hasFirstClassOutlets;
    }

    public boolean isHasHeating() {
        return hasHeating;
    }

    public void setHasHeating(boolean hasHeating) {
        this.hasHeating = hasHeating;
    }

    public boolean isHasAirco() {
        return hasAirco;
    }

    public void setHasAirco(boolean hasAirco) {
        this.hasAirco = hasAirco;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String meterialNumber) {
        this.materialNumber = meterialNumber;
    }

    public String getTractionType() {
        return tractionType;
    }

    public void setTractionType(String tractionType) {
        this.tractionType = tractionType;
    }

    public boolean isCanPassToNextUnit() {
        return canPassToNextUnit;
    }

    public void setCanPassToNextUnit(boolean canPassToNextUnit) {
        this.canPassToNextUnit = canPassToNextUnit;
    }

    public int getStandingPlacesSecondClass() {
        return standingPlacesSecondClass;
    }

    public void setStandingPlacesSecondClass(int standingPlacesSecondClass) {
        this.standingPlacesSecondClass = standingPlacesSecondClass;
    }

    public int getStandingPlacesFirstClass() {
        return standingPlacesFirstClass;
    }

    public void setStandingPlacesFirstClass(int standingPlacesFirstClass) {
        this.standingPlacesFirstClass = standingPlacesFirstClass;
    }

    public int getSeatsCoupeSecondClass() {
        return seatsCoupeSecondClass;
    }

    public void setSeatsCoupeSecondClass(int seatsCoupeSecondClass) {
        this.seatsCoupeSecondClass = seatsCoupeSecondClass;
    }

    public int getSeatsCoupeFirstClass() {
        return seatsCoupeFirstClass;
    }

    public void setSeatsCoupeFirstClass(int seatsCoupeFirstClass) {
        this.seatsCoupeFirstClass = seatsCoupeFirstClass;
    }

    public int getSeatsSecondClass() {
        return seatsSecondClass;
    }

    public void setSeatsSecondClass(int seatsSecondClass) {
        this.seatsSecondClass = seatsSecondClass;
    }

    public int getSeatsFirstClass() {
        return seatsFirstClass;
    }

    public void setSeatsFirstClass(int seatsFirstClass) {
        this.seatsFirstClass = seatsFirstClass;
    }

    public int getLengthInMeter() {
        return lengthInMeter;
    }

    public void setLengthInMeter(int lengthInMeter) {
        this.lengthInMeter = lengthInMeter;
    }

    public boolean isHasSemiAutomaticInteriorDoors() {
        return hasSemiAutomaticInteriorDoors;
    }

    public void setHasSemiAutomaticInteriorDoors(boolean hasSemiAutomaticInteriorDoors) {
        this.hasSemiAutomaticInteriorDoors = hasSemiAutomaticInteriorDoors;
    }

    public boolean isHasLuggageSection() {
        return hasLuggageSection;
    }

    public void setHasLuggageSection(boolean hasLuggageSection) {
        this.hasLuggageSection = hasLuggageSection;
    }

    public String getMaterialSubTypeName() {
        return materialSubTypeName;
    }

    public void setMaterialSubTypeName(String materialSubTypeName) {
        this.materialSubTypeName = materialSubTypeName;
    }

    public int getTractionPosition() {
        return tractionPosition;
    }

    public void setTractionPosition(int tractionPosition) {
        this.tractionPosition = tractionPosition;
    }

    public boolean isHasPrmSection() {
        return hasPrmSection;
    }

    public void setHasPrmSection(boolean hasPrmSection) {
        this.hasPrmSection = hasPrmSection;
    }

    public boolean isHasPriorityPlaces() {
        return hasPriorityPlaces;
    }

    public void setHasPriorityPlaces(boolean hasPriorityPlaces) {
        this.hasPriorityPlaces = hasPriorityPlaces;
    }

    public boolean isHasBikeSection() {
        return hasBikeSection;
    }

    public void setHasBikeSection(boolean hasBikeSection) {
        this.hasBikeSection = hasBikeSection;
    }
}
