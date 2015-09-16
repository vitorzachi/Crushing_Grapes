package br.edu.unochapeco.crushinggrapes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import AndGraph.AGGameManager;
import AndGraph.AGInputManager;
import AndGraph.AGScene;
import AndGraph.AGScreenManager;
import AndGraph.AGSoundManager;
import AndGraph.AGSprite;
import AndGraph.AGTimer;

import br.edu.unochapeco.crushinggrapes.comp.Grape;

/**
 * Created by everton on 08/09/15.
 */
public class Game extends AGScene {
    private AGTimer timerCreateGrape;
    private List<Grape> listUvas;
    private Random sorteioPosUva;
    private Random sorteioTipoUva;
    private int placar;



    public Game(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {
        listUvas = new ArrayList<Grape>();

        AGSprite fundo = createSprite(R.drawable.pipa, 1,1);
        Som.somJogo();

        fundo.setScreenPercent(100, 100);
        fundo.vrPosition.fX = AGScreenManager.iScreenWidth / 2;
        fundo.vrPosition.fY = AGScreenManager.iScreenHeight / 2;

        timerCreateGrape = new AGTimer();
        timerCreateGrape.restart(500);

        this.sorteioPosUva = new Random();
        this.sorteioTipoUva = new Random();

        placar = 0;

        criarUvasRandom();

    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {
        AGSoundManager.vrMusic.stop();
    }

    private float getRandomUvaFX(float widthGrape) {
        float min = widthGrape/2;
        float max = AGScreenManager.iScreenWidth - min;

        return min + this.sorteioTipoUva.nextFloat() * (max - min);
    }

    private boolean isGoodGrape() {
        return this.sorteioTipoUva.nextBoolean();
    }

    private void criarUvasRandom() {
        timerCreateGrape.update();

        if (timerCreateGrape.isTimeEnded()) {
            AGSprite aux;

            for (Grape g: listUvas) {
                aux = g.getCompGame();
                if (aux.bRecycled) {

                    aux.bRecycled = false;
                    aux.bVisible = true;
                    aux.vrPosition.fX = getRandomUvaFX(aux.getSpriteWidth());
                    aux.vrPosition.fY = AGScreenManager.iScreenHeight  + aux.getSpriteHeight() / 2;

                    timerCreateGrape.restart();
                    return;
                }
            }

            boolean grapeGood = isGoodGrape();
            if (grapeGood)
                aux = createSprite(R.drawable.uva, 1, 1);
            else
                aux = createSprite(R.drawable.estragada, 1, 1);

            aux.setScreenPercent(30, 10);
            aux.vrPosition.fX = getRandomUvaFX(aux.getSpriteWidth());
            aux.vrPosition.fY = AGScreenManager.iScreenHeight  + aux.getSpriteHeight() / 2;

            listUvas.add(new Grape(aux, grapeGood));

           timerCreateGrape.restart();
        }
    }

    private void moverUva() {
        AGSprite aux;
        for (Grape g: listUvas) {
            aux = g.getCompGame();

            if (aux.bRecycled) continue;

            aux.vrPosition.fY -= 10; //velocidade mexer aqui

            if (aux.vrPosition.fY <= -(aux.getSpriteHeight() / 2))
                aux.bRecycled = true;
        }
    }

    private void tratarUvasEsmagadas() {
        if (AGInputManager.vrTouchEvents.screenClicked()) {

            AGSprite aux;

            for (Grape g : listUvas) {
                aux = g.getCompGame();

                if (aux.bRecycled) continue;

                if (aux.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                    aux.bRecycled = true;
                    aux.bVisible = false;

                    if (g.isGrapeGood())
                        placar++;
                    else
                        --placar;

                    break;
                }
            }
        }
    }


    @Override
    public void loop() {
        criarUvasRandom();
        moverUva();
        tratarUvasEsmagadas();
    }



}
