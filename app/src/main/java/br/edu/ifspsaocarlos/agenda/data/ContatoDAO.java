package br.edu.ifspsaocarlos.agenda.data;

import br.edu.ifspsaocarlos.agenda.model.Contato;
import io.realm.Realm;
import io.realm.RealmResults;

public class ContatoDAO {
    private Realm database;
    private final String KEY_NAME = "nome";
    private final String KEY_ID = "id";

    public ContatoDAO() {
        database = Realm.getDefaultInstance();
    }

    public RealmResults<Contato> buscaTodosContatos() {
        RealmResults<Contato> contatos = database.where(Contato.class).findAll();
        return contatos;
    }

    public RealmResults<Contato> buscaContato(String nome) {
        RealmResults<Contato> contatos = database.where(Contato.class).contains(KEY_NAME, nome).findAll();
        return contatos;
    }

    public Contato buscaContato(long id) {
        RealmResults<Contato> contatos = database.where(Contato.class).equalTo(KEY_ID, id).findAll();
        return contatos.first();
    }

    public void salvaContato(Contato c) {
        database.beginTransaction();
        database.copyToRealmOrUpdate(c);
        database.commitTransaction();
    }

    public void apagaContato(Contato c) {
        final Contato cDelete =
                database.where(Contato.class).equalTo(KEY_ID, c.getId()).findFirst();
        if (cDelete != null) {
            database.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    cDelete.deleteFromRealm();
                }
            });
        }
    }

    public void finish(){
        database.close();
    }
}