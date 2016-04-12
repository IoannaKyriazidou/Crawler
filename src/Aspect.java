package Main;

public class Aspect {

    String lemma;
    int id;
    int aspectCategoryId;
    int entityCategoryId;
    int rank;

    public Aspect(String asp, int id_i, int aspCatId, int entCatId, int ran) {

        this.lemma = asp;
        this.id = id_i;
        this.aspectCategoryId = aspCatId;
        this.entityCategoryId = entCatId;
        this.rank = ran;
    }

}
