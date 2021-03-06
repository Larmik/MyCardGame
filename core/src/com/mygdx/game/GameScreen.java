package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private MyGame game;
    private Texture cardBackground, backCard;
    private Image deckImg;
    private List<Image> cardImagesPlayer, whiteBackgroundImages, cardImagesIA1, cardImagesIA2, deckImgs;
    private int screenHeight, screenWidth, cardHeight, cardWidth, firstPlayerCardX, firstPlayerCardY, firstIA1CardY, selectedCard, score, playerTurn = 0, numberOfPlayers;
    private boolean isAs11 = false, isPlusDix = false, reverse = false;
    private Stage stage;
    private BitmapFont fontScore, fontDeck;
    private TextButton playCardButton, dixPlus, dixMoins, as1, as11;
    private List<Card> deck, playerGame, ia1Game, ia2Game;

    GameScreen(MyGame game, int numberOfPlayers) {
        this.game = game;
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public void show() {
        initViews();
    }

    @Override
    public void render(float delta) {
        if (playerTurn == 0)
            setInputProcessor();
        displaySelectedCard();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (deck.size() == 0 && ia1Game.size() == 0 && playerGame.size() == 0)
                    game.setScreen(new ResultScreen(game, false, true, numberOfPlayers));
            }
        }, 1f);
        stage.act();
        stage.draw();
        stage.getBatch().begin();
        fontScore.getData().setScale(2.8f);
        fontDeck.getData().setScale(1.2f);
        fontScore.draw(stage.getBatch(), "Score: " + String.valueOf(score), firstPlayerCardX + cardWidth - cardWidth / 4, screenHeight / 2 - cardWidth / 2);
        fontDeck.draw(stage.getBatch(), "Restantes: " + String.valueOf(deck.size()), firstPlayerCardX, screenHeight - cardWidth + 50);
        stage.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        stage.dispose();
        fontScore.dispose();
        fontDeck.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        fontScore.dispose();
        fontDeck.dispose();
    }

    private void createPlayerCardImage(int position, Card card) {
        Image nextCardImg = new Image(new Texture(Gdx.files.local(card.getAsset())));
        Image backImg = new Image(cardBackground);
        nextCardImg.setSize(cardWidth, cardHeight);
        backImg.setSize(cardWidth, cardHeight);
        switch (position) {
            case 1:
                nextCardImg.setPosition(firstPlayerCardX, firstPlayerCardY);
                backImg.setPosition(firstPlayerCardX, firstPlayerCardY);
                break;
            case 2:
                nextCardImg.setPosition(firstPlayerCardX + cardWidth, firstPlayerCardY);
                backImg.setPosition(firstPlayerCardX + cardWidth, firstPlayerCardY);
                break;
            case 3:
                nextCardImg.setPosition(firstPlayerCardX + cardWidth * 2, firstPlayerCardY);
                backImg.setPosition(firstPlayerCardX + cardWidth * 2, firstPlayerCardY);
                break;
        }
        cardImagesPlayer.add(nextCardImg);
        whiteBackgroundImages.add(backImg);
        playerGame.add(card);
        stage.addActor(backImg);
        stage.addActor(nextCardImg);

    }

    private void createIACardImage(int position, Card card, int totalPlayers) {
        Image nextCardImg = new Image(backCard);

        switch (totalPlayers) {
            case 1:
                nextCardImg.setSize(cardHeight, cardWidth);
                switch (position) {
                    case 1:
                        nextCardImg.setPosition(-cardHeight / 2, firstIA1CardY + cardWidth - cardWidth / 2);
                        break;
                    case 2:
                        nextCardImg.setPosition(-cardHeight / 2, firstIA1CardY + cardWidth);
                        break;
                    case 3:
                        nextCardImg.setPosition(-cardHeight / 2, firstIA1CardY + cardWidth * 2 - cardWidth / 2);
                        break;
                }
                if (!ia1Game.contains(card))
                    ia1Game.add(card);
                cardImagesIA1.add(nextCardImg);
                break;
            case 2:
                nextCardImg.setSize(cardHeight, cardWidth);
                switch (position) {
                    case 1:
                        nextCardImg.setPosition(screenWidth - cardHeight / 2, firstIA1CardY + cardWidth - cardWidth / 2);
                        break;
                    case 2:
                        nextCardImg.setPosition(screenWidth - cardHeight / 2, firstIA1CardY + cardWidth);
                        break;
                    case 3:
                        nextCardImg.setPosition(screenWidth - cardHeight / 2, firstIA1CardY + cardWidth * 2 - cardWidth / 2);
                        break;
                }
                if (!ia2Game.contains(card))
                    ia2Game.add(card);
                cardImagesIA2.add(nextCardImg);
                break;
            case 3:
                nextCardImg.setSize(cardWidth, cardHeight);
                switch (position) {
                    case 1:
                        nextCardImg.setPosition(firstPlayerCardX, screenHeight + firstPlayerCardY);
                        break;
                    case 2:
                        nextCardImg.setPosition(firstPlayerCardX + cardWidth, screenHeight + firstPlayerCardY);
                        break;
                    case 3:
                        nextCardImg.setPosition(firstPlayerCardX + cardWidth * 2, screenHeight + firstPlayerCardY);
                        break;
                }
                break;
        }
        stage.addActor(nextCardImg);
    }

    private void resetSelection() {
        Gdx.input.setInputProcessor(null);
        selectedCard = 0;
        playCardButton.setVisible(false);
        dixPlus.setVisible(false);
        dixMoins.setVisible(false);
        as1.setVisible(false);
        as11.setVisible(false);
    }

    private void createNextCardImage() {
        deckImgs.clear();
        Image nextCard = new Image(backCard);
        deckImgs.add(nextCard);

    }

    private void calculateScore(CardValue value, boolean isPlusDix, boolean isAs1) {
        switch (value) {
            case SEPT:
                if (numberOfPlayers == 2)
                    reverse = !reverse;
                break;
            case NEUF:
                break;
            case HUIT:
                score += 8;
                break;
            case DIX:
                if (isPlusDix)
                    score += 10;
                else
                    score -= 10;
                break;
            case VALET:
                score += 2;
                break;
            case DAME:
                score += 3;
                break;
            case ROI:
                score += 4;
                break;
            case AS:
                if (isAs1)
                    score += 1;
                else
                    score += 11;
                break;
        }
    }

    private void playPlayer() {
        whiteBackgroundImages.get(selectedCard - 1).toFront();
        cardImagesPlayer.get(selectedCard - 1).toFront();
        cardImagesPlayer.get(selectedCard - 1).addAction(Actions.moveTo(firstPlayerCardX + cardWidth * 2, screenHeight - (cardHeight + cardWidth), 20, new Interpolation.ExpOut(5, 200)));
        whiteBackgroundImages.get(selectedCard - 1).addAction(Actions.moveTo(firstPlayerCardX + cardWidth * 2, screenHeight - (cardHeight + cardWidth), 20, new Interpolation.ExpOut(5, 200)));
        if (deck.size() != 0) {
            createPlayerCardImage(selectedCard, deck.get(0));
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    createNextCardImage();
                    pickPlayer();
                }
            }, 0.7f);
        }
        cardImagesPlayer.remove(cardImagesPlayer.get(selectedCard - 1));
        whiteBackgroundImages.remove(whiteBackgroundImages.get(selectedCard - 1));
        playerGame.remove(playerGame.get(selectedCard - 1));
        if (deck.size() == 0) {
            cardImagesPlayer.add(deckImg);
            whiteBackgroundImages.add(deckImg);
            cardImagesPlayer.get(2).setVisible(false);
            whiteBackgroundImages.get(2).setVisible(false);
        } else {
            cardImagesPlayer.get(cardImagesPlayer.size() - 1).setVisible(false);
            whiteBackgroundImages.get(cardImagesPlayer.size() - 1).setVisible(false);
        }
        if (!reverse) {
            if (!ia1Game.isEmpty()) {
                updatePlayerTurn();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        playIA(playerTurn, cardImagesIA1, ia1Game);
                        if (!ia2Game.isEmpty()) {
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    if (playerTurn != 0) {
                                        playIA(playerTurn, cardImagesIA2, ia2Game);
                                        if (reverse && playerTurn == 2 && !ia1Game.isEmpty()) {
                                            Timer.schedule(new Timer.Task() {
                                                @Override
                                                public void run() {
                                                    playIA(playerTurn, cardImagesIA1, ia1Game);
                                                    if (!reverse && playerTurn == 1 && !ia2Game.isEmpty()) {
                                                        Timer.schedule(new Timer.Task() {
                                                            @Override
                                                            public void run() {
                                                                playIA(playerTurn, cardImagesIA2, ia2Game);
                                                                if (reverse && playerTurn == 2 && !ia1Game.isEmpty()) {
                                                                    Timer.schedule(new Timer.Task() {
                                                                        @Override
                                                                        public void run() {
                                                                            playIA(playerTurn, cardImagesIA1, ia1Game);
                                                                        }
                                                                    }, 1f);
                                                                }
                                                            }
                                                        }, 1f);
                                                    }
                                                }
                                            }, 1f);
                                        }
                                    }
                                }
                            }, 1f);
                        }
                    }
                }, 1f);
            }
        } else {
            if (!ia2Game.isEmpty()) {
                updatePlayerTurn();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        playIA(playerTurn, cardImagesIA2, ia2Game);
                        if (!ia1Game.isEmpty()) {
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    if (playerTurn != 0) {
                                        playIA(playerTurn, cardImagesIA1, ia1Game);
                                        if (!reverse && playerTurn == 1 && !ia2Game.isEmpty()) {
                                            Timer.schedule(new Timer.Task() {
                                                @Override
                                                public void run() {
                                                    playIA(playerTurn, cardImagesIA2, ia2Game);
                                                    if (reverse && playerTurn == 2 && !ia1Game.isEmpty()) {
                                                        Timer.schedule(new Timer.Task() {
                                                            @Override
                                                            public void run() {
                                                                playIA(playerTurn, cardImagesIA1, ia1Game);
                                                                if (!reverse && playerTurn == 1 && !ia2Game.isEmpty()) {
                                                                    Timer.schedule(new Timer.Task() {
                                                                        @Override
                                                                        public void run() {
                                                                            playIA(playerTurn, cardImagesIA1, ia1Game);
                                                                        }
                                                                    }, 1f);
                                                                }
                                                            }
                                                        }, 1f);
                                                    }
                                                }
                                            }, 1f);
                                        }
                                    }
                                }
                            }, 1f);
                        }
                    }
                }, 1f);
            }
        }
    }

    private void pickPlayer() {
        deckImgs.get(0).addAction(Actions.moveTo(firstPlayerCardX + cardWidth * 2, firstPlayerCardY, 20, new Interpolation.ExpOut(5, 200)));
        deckImgs.get(0).setSize(cardWidth, cardHeight);
        deckImgs.get(0).setPosition(firstPlayerCardX, screenHeight - (cardHeight + cardWidth));
        stage.addActor(deckImgs.get(0));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                deckImgs.get(0).toBack();
                cardImagesPlayer.get(2).setVisible(true);
                whiteBackgroundImages.get(2).setVisible(true);
                whiteBackgroundImages.get(2).toFront();
                cardImagesPlayer.get(2).toFront();
                if (deck.size() == 0) {
                    deckImg.setVisible(false);
                }
            }
        }, 0.1f);
        if (deck.size() != 0) {
            deck.remove(0);
        }
    }

    private void playIA(final int whitch, final List<Image> cardImages, List<Card> cardGame) {
        if (getPlayedIACard(cardGame) == null)
            game.setScreen(new ResultScreen(game, true, false, numberOfPlayers));
        if (cardGame.isEmpty())
            game.setScreen(new ResultScreen(game, false, true, numberOfPlayers));
        Image backCardImg = new Image(backCard);
        final Image nextCardImg = new Image(new Texture(Gdx.files.local(getPlayedIACard(cardGame).getAsset())));
        final Image backImg = new Image(cardBackground);
        Card card = getPlayedIACard(cardGame);
        if (Card.isTen(card))
            calculateScore(CardValue.DIX, score <= 40, false);
        else if (Card.isAce(card)) {
            calculateScore(CardValue.AS, false, score > 39);
        } else
            calculateScore(Card.getCardValue(card.getAsset()), false, false);
        backCardImg.setSize(cardWidth, cardHeight);
        nextCardImg.setSize(cardWidth, cardHeight);
        backImg.setSize(cardWidth, cardHeight);
        backImg.setPosition(firstPlayerCardX + cardWidth * 2, screenHeight - (cardHeight + cardWidth));
        nextCardImg.setPosition(firstPlayerCardX + cardWidth * 2, screenHeight - (cardHeight + cardWidth));
        switch (whitch) {
            case 1:
                backCardImg.setPosition(-cardHeight / 2, firstIA1CardY + cardWidth);
                break;
            case 2:
                backCardImg.setPosition(screenWidth - cardHeight / 2, firstIA1CardY + cardWidth);
                break;
            case 3:
                backCardImg.setPosition(firstPlayerCardX + cardWidth, screenHeight + firstPlayerCardY);
                break;
        }
        backCardImg.addAction(Actions.moveTo(firstPlayerCardX + cardWidth * 2, screenHeight - (cardHeight + cardWidth), 20, new Interpolation.ExpOut(5, 200)));
        stage.addActor(backCardImg);
        stage.addActor(backImg);
        stage.addActor(nextCardImg);
        nextCardImg.setVisible(false);
        backImg.setVisible(false);
        createIACardImage(2, getSelectedIACard(cardGame), playerTurn);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                nextCardImg.setVisible(true);
                backImg.setVisible(true);
                backImg.toFront();
                nextCardImg.toFront();
                deckImgs.get(0).toBack();
                if (deck.size() == 0) {
                    deckImg.setVisible(false);
                    updatePlayerTurn();
                } else if (playerTurn != 0) {
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            pickIA(whitch, cardImages);
                        }
                    }, 0.7f);
                }
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (((whitch == numberOfPlayers && !reverse) || (whitch == 1 && reverse)) && getPlayedIACard(playerGame) == null && !playerGame.isEmpty())
                            game.setScreen(new ResultScreen(game, false, false, numberOfPlayers));
                        if (gameOver())
                            game.setScreen(new ResultScreen(game, false, true, numberOfPlayers));

                    }
                }, 0.6f);
            }
        }, 0.15f);
        cardImages.remove(cardImages.get(cardImages.size() - 1));
        cardGame.remove(card);
        if (!cardGame.isEmpty())
            cardImages.get(cardGame.size() - 1).setVisible(false);
        cardImages.get(cardImages.size() - 1).setVisible(false);
    }

    private void pickIA(final int which, final List<Image> cardImages) {
        updatePlayerTurn();
        createNextCardImage();
        switch (which) {
            case 1:
                deckImgs.get(0).addAction(Actions.moveTo(-cardHeight / 2, firstIA1CardY + cardWidth, 20, new Interpolation.ExpOut(5, 200)));
                break;
            case 2:
                deckImgs.get(0).addAction(Actions.moveTo(screenWidth - cardHeight / 2, firstIA1CardY + cardWidth, 20, new Interpolation.ExpOut(5, 200)));
                break;
            case 3:
                deckImgs.get(0).addAction(Actions.moveTo(firstPlayerCardX + cardWidth, screenHeight + firstPlayerCardY, 20, new Interpolation.ExpOut(5, 200)));
                break;
        }
        deckImgs.get(0).setSize(cardWidth, cardHeight);
        deckImgs.get(0).setPosition(firstPlayerCardX, screenHeight - (cardHeight + cardWidth));
        stage.addActor(deckImgs.get(0));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                deckImgs.get(0).toBack();
                cardImages.get(2).setVisible(true);
                cardImages.get(2).toFront();
                if (deck.size() == 0) {
                    deckImg.setVisible(false);
                }

            }
        }, 0.1f);
        if (deck.size() != 0) {
            deck.remove(0);
        }
    }

    private void displaySelectedCard() {
        if (selectedCard == 1) {
            cardImagesPlayer.get(0).addAction(Actions.moveTo(firstPlayerCardX, firstPlayerCardY + 50));
            whiteBackgroundImages.get(0).addAction(Actions.moveTo(firstPlayerCardX, firstPlayerCardY + 50));
        } else {
            cardImagesPlayer.get(0).addAction(Actions.moveTo(firstPlayerCardX, firstPlayerCardY));
            whiteBackgroundImages.get(0).addAction(Actions.moveTo(firstPlayerCardX, firstPlayerCardY));
        }
        if (selectedCard == 2) {
            cardImagesPlayer.get(1).addAction(Actions.moveTo(firstPlayerCardX + cardWidth, firstPlayerCardY + 50));
            whiteBackgroundImages.get(1).addAction(Actions.moveTo(firstPlayerCardX + cardWidth, firstPlayerCardY + 50));
        } else {
            cardImagesPlayer.get(1).addAction(Actions.moveTo(firstPlayerCardX + cardWidth, firstPlayerCardY));
            whiteBackgroundImages.get(1).addAction(Actions.moveTo(firstPlayerCardX + cardWidth, firstPlayerCardY));
        }
        if (selectedCard == 3) {
            cardImagesPlayer.get(2).addAction(Actions.moveTo(firstPlayerCardX + cardWidth * 2, firstPlayerCardY + 50));
            whiteBackgroundImages.get(2).addAction(Actions.moveTo(firstPlayerCardX + cardWidth * 2, firstPlayerCardY + 50));
        } else {
            cardImagesPlayer.get(2).addAction(Actions.moveTo(firstPlayerCardX + cardWidth * 2, firstPlayerCardY));
            whiteBackgroundImages.get(2).addAction(Actions.moveTo(firstPlayerCardX + cardWidth * 2, firstPlayerCardY));
        }
    }

    private Card getSelectedIACard(List<Card> cards) {
        if (deck.isEmpty() && !cards.isEmpty()) {
            return cards.get(cards.size() - 1);
        }
        return deck.get(0);
    }

    private Card getPlayedIACard(List<Card> cards) {
        Card returnedCard = null;
        int priority = CardSelectionUtil.CardPriority.LOOSED.getValue();
        for (Card card : cards) {
            if (CardSelectionUtil.getPriority(card, score, score < 41, score > 40).getValue() < priority) {
                priority = CardSelectionUtil.getPriority(card, score, score < 41, score > 40).getValue();
                returnedCard = card;
            }
        }
        return returnedCard;
    }

    private void initViews() {
        deck = Card.getDeck();

        Texture background = new Texture(Gdx.files.local("assets/background.jpg"));
        Texture deckTexture = new Texture(Gdx.files.local(deck.get(0).getAsset()));
        backCard = new Texture(Gdx.files.local("assets/cards/back.png"));
        cardBackground = new Texture(Gdx.files.local("assets/cards/white.jpg"));
        fontScore = new BitmapFont(Gdx.files.local("assets/fonts/junegull.fnt"));
        fontDeck = new BitmapFont(Gdx.files.local("assets/fonts/junegull.fnt"));
        stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("skins/neon-ui.json"));
        Image backgroundImg = new Image(background);
        deckImg = new Image(backCard);
        playCardButton = new TextButton("Jouer", skin);
        as1 = new TextButton("+1", skin);
        as11 = new TextButton("+11", skin);
        dixPlus = new TextButton("+10", skin);
        dixMoins = new TextButton("-10", skin);
        cardImagesPlayer = new ArrayList<Image>();
        cardImagesIA1 = new ArrayList<Image>();
        cardImagesIA2 = new ArrayList<Image>();
        deckImgs = new ArrayList<Image>();
        playerGame = new ArrayList<Card>();
        ia1Game = new ArrayList<Card>();
        ia2Game = new ArrayList<Card>();
        whiteBackgroundImages = new ArrayList<Image>();
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        cardWidth = deckTexture.getWidth();
        cardHeight = deckTexture.getHeight();
        firstPlayerCardX = screenWidth / 2 - cardWidth / 2 - cardWidth;
        firstPlayerCardY = -cardHeight / 2;
        firstIA1CardY = screenHeight / 2 - cardWidth / 2 - cardWidth;

        playCardButton.setSize(cardWidth, cardWidth / 2);
        deckImg.setSize(cardWidth, cardHeight);
        backgroundImg.setSize(screenWidth, screenHeight);
        as1.setSize(cardWidth, cardWidth / 2);
        as11.setSize(cardWidth, cardWidth / 2);
        dixPlus.setSize(cardWidth, cardWidth / 2);
        dixMoins.setSize(cardWidth, cardWidth / 2);

        playCardButton.getLabel().setFontScale(2.5f);
        dixPlus.getLabel().setFontScale(2.5f);
        dixMoins.getLabel().setFontScale(2.5f);
        as1.getLabel().setFontScale(2.5f);
        as11.getLabel().setFontScale(2.5f);

        playCardButton.setVisible(false);
        dixPlus.setVisible(false);
        dixMoins.setVisible(false);
        as1.setVisible(false);
        as11.setVisible(false);

        backgroundImg.setPosition(0, 0);
        playCardButton.setPosition(firstPlayerCardX + cardWidth, cardWidth);
        deckImg.setPosition(firstPlayerCardX, screenHeight - (cardHeight + cardWidth));
        as1.setPosition(firstPlayerCardX, cardWidth);
        as11.setPosition(firstPlayerCardX + cardWidth * 2, cardWidth);
        dixPlus.setPosition(firstPlayerCardX, cardWidth);
        dixMoins.setPosition(firstPlayerCardX + cardWidth * 2, cardWidth);

        playCardButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isAs11 = false;
                isPlusDix = false;
                Card card = playerGame.get(selectedCard - 1);
                if (Card.isTen(card)) {
                    if (score <= 10) {
                        calculateScore(CardValue.DIX, true, false);
                    } else if (score > 40 && !isPlusDix) {
                        calculateScore(CardValue.DIX, false, false);
                        isPlusDix = false;
                    } else {
                        playCardButton.setVisible(false);
                        dixMoins.setVisible(true);
                        dixPlus.setVisible(true);
                    }
                } else if (Card.isAce(card)) {
                    if (score > 39 && !isAs11) {
                        calculateScore(CardValue.AS, false, true);
                        isAs11 = false;
                    } else {
                        playCardButton.setVisible(false);
                        as11.setVisible(true);
                        as1.setVisible(true);
                    }
                } else
                    calculateScore(Card.getCardValue(card.getAsset()), false, false);
                playPlayer();
                resetSelection();
                return false;
            }
        });
        dixMoins.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPlusDix = false;
                calculateScore(CardValue.DIX, false, false);
                playPlayer();
                resetSelection();
                return false;
            }
        });
        dixPlus.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPlusDix = true;
                calculateScore(CardValue.DIX, true, false);
                playPlayer();
                resetSelection();
                return false;
            }
        });
        as1.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isAs11 = false;
                calculateScore(CardValue.AS, false, true);
                playPlayer();
                resetSelection();
                return false;
            }
        });
        as11.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isAs11 = true;
                calculateScore(CardValue.AS, false, false);
                playPlayer();
                resetSelection();
                return false;
            }
        });

        stage.addActor(backgroundImg);
        stage.addActor(playCardButton);
        stage.addActor(deckImg);
        stage.addActor(as1);
        stage.addActor(as11);
        stage.addActor(dixMoins);
        stage.addActor(dixPlus);

        for (int i = 1; i <= 3; i++) {
            createPlayerCardImage(i, deck.get(0));
            if (deck.size() != 0) {
                deck.remove(0);
            }
            for (int j = 1; j <= numberOfPlayers; j++) {
                createIACardImage(i, deck.get(0), j);
                if (deck.size() != 0) {
                    deck.remove(0);
                }
            }
        }
        if (playerTurn != 0) {
            playIA(1, cardImagesIA1, ia1Game);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    createNextCardImage();
                    pickIA(1, cardImagesIA1);
                    if (!ia2Game.isEmpty()) {
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                playIA(2, cardImagesIA2, ia2Game);
                            }
                        }, 0.7f);
                    }
                }
            }, 0.7f);
        }
        setInputProcessor();
    }

    private void setInputProcessor() {
        InputMultiplexer multiInput = new InputMultiplexer();
        multiInput.addProcessor(new InputProcessor() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int boutton) {
                if (screenX >= firstPlayerCardX && screenX <= firstPlayerCardX + cardWidth && screenY < screenHeight && screenY >= screenHeight - cardHeight / 2) {
                    selectedCard = 1;
                    playCardButton.setVisible(true);
                } else if (screenX > firstPlayerCardX + cardWidth && screenX <= firstPlayerCardX + cardWidth * 2 && screenY < screenHeight && screenY >= screenHeight - cardHeight / 2) {
                    selectedCard = 2;
                    playCardButton.setVisible(true);
                } else if (screenX > firstPlayerCardX + cardWidth * 2 && screenX <= firstPlayerCardX + cardWidth * 3 && screenY < screenHeight && screenY >= screenHeight - cardHeight / 2) {
                    selectedCard = 3;
                    playCardButton.setVisible(true);
                } else
                    return false;
                if (Card.isTen(playerGame.get(selectedCard - 1)) && score > 10 && score < 41) {
                    playCardButton.setVisible(false);
                    dixMoins.setVisible(true);
                    dixPlus.setVisible(true);
                } else {
                    dixMoins.setVisible(false);
                    dixPlus.setVisible(false);
                }
                if (Card.isAce(playerGame.get(selectedCard - 1)) && score < 40) {
                    playCardButton.setVisible(false);
                    as11.setVisible(true);
                    as1.setVisible(true);
                } else {
                    as1.setVisible(false);
                    as11.setVisible(false);
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }

            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

        });
        multiInput.addProcessor(stage);
        Gdx.input.setInputProcessor(multiInput);
    }

    private void updatePlayerTurn() {
        if (!reverse) {
            if (playerTurn == numberOfPlayers)
                playerTurn = 0;
            else
                playerTurn++;
        } else {
            if (playerTurn == 0)
                playerTurn = numberOfPlayers;
            else
                playerTurn--;
        }

    }

    private boolean gameOver() {
        return ia1Game.isEmpty() && ia2Game.isEmpty() && playerGame.isEmpty();
    }
}