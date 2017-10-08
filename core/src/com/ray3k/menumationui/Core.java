/*
 * The MIT License
 *
 * Copyright 2017 Raymond Buckley.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.ray3k.menumationui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.ray3k.menumationui.SpineDrawable.SpineDrawableTemplate;

public class Core extends ApplicationAdapter {
    private SplashWorker splashWorker;
    private Skin skin;
    private Stage stage;

    @Override
    public void create() {
        splashWorker.closeSplashScreen();
        SkeletonJson skeletonJson = new SkeletonJson(new TextureAtlas(Gdx.files.internal("menumation-ui-spine.atlas")));
        SkeletonRenderer skeletonRenderer = new SkeletonRenderer();
        skeletonRenderer.setPremultipliedAlpha(true);
        skin = new Skin(Gdx.files.internal("menumation-ui.json"));
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        SpineDrawableTemplate template = skin.get("window", SpineDrawableTemplate.class);
        final SpineDrawable windowDrawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        Window.WindowStyle windowStyle = new Window.WindowStyle(skin.get(Window.WindowStyle.class));
        windowStyle.background = windowDrawable;
        final Window window = new Window("Menumation UI", windowStyle);
        window.getTitleLabel().setAlignment(Align.center);
        window.getTitleLabel().setColor(1, 1, 1, 0);
        windowDrawable.getAnimationState().setAnimation(0, "start", false);
        windowDrawable.getAnimationState().addAnimation(0, "loading", true, 0);
        window.setSize(700.0f, 700.0f);
        window.setPosition(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, Align.center);
        stage.addActor(window);
        
        window.addAction(Actions.sequence(Actions.delay(5.0f), new Action() {
            @Override
            public boolean act(float delta) {
                windowDrawable.getAnimationState().setAnimation(0, "show-menu", false);
                return true;
            }
        }));
        
        window.defaults().space(10.0f);
        
        template = skin.get("button", SpineDrawableTemplate.class);
        final SpineDrawable textButton1Drawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        final TextButton textButton1 = new TextButton("Play", skin);
        textButton1.setStyle(new TextButton.TextButtonStyle(textButton1.getStyle()));
        textButton1.getStyle().up = textButton1Drawable;
        textButton1Drawable.getAnimationState().setAnimation(0, "invisible", false);
        textButton1Drawable.getAnimationState().apply(textButton1Drawable.getSkeleton());
        textButton1.getLabel().setColor(1, 1, 1, 0);
        window.add(textButton1);
        
        textButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                textButton1Drawable.getAnimationState().setAnimation(0, "pressed", false);
            }
        });
        
        final SpineDrawable textButton2Drawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        final TextButton textButton2 = new TextButton("Options", skin);
        textButton2.setStyle(new TextButton.TextButtonStyle(textButton2.getStyle()));
        textButton2.getStyle().up = textButton2Drawable;
        textButton2Drawable.getAnimationState().setAnimation(0, "invisible", false);
        textButton2Drawable.getAnimationState().apply(textButton2Drawable.getSkeleton());
        textButton2.getLabel().setColor(1, 1, 1, 0);
        window.add(textButton2);
        
        textButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                textButton2Drawable.getAnimationState().setAnimation(0, "pressed", false);
            }
        });
        
        final SpineDrawable textButton3Drawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        final TextButton textButton3 = new TextButton("Quit", skin);
        textButton3.setStyle(new TextButton.TextButtonStyle(textButton3.getStyle()));
        textButton3.getStyle().up = textButton3Drawable;
        textButton3Drawable.getAnimationState().setAnimation(0, "invisible", false);
        textButton3Drawable.getAnimationState().apply(textButton3Drawable.getSkeleton());
        textButton3.getLabel().setColor(1, 1, 1, 0);
        window.add(textButton3);
        
        textButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                textButton3Drawable.getAnimationState().setAnimation(0, "pressed", false);
            }
        });
        
        template = skin.get("select-box", SpineDrawableTemplate.class);
        final SpineDrawable selectBoxDrawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        window.defaults().colspan(3);
        window.row();
        final SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Test", "Trial", "Simulation", "Case", "Study", "Dry Run", "Experiment", "Examination", "Procedure", "Pilot", "Demo", "Tryout");
        selectBox.setStyle(new SelectBox.SelectBoxStyle(selectBox.getStyle()));
        selectBox.getStyle().background = selectBoxDrawable;
        selectBoxDrawable.getAnimationState().setAnimation(0, "invisible", false);
        selectBoxDrawable.getAnimationState().apply(selectBoxDrawable.getSkeleton());
        selectBox.getStyle().fontColor.a = 0.0f;
        window.add(selectBox);
        
        template = skin.get("scrollbar-vertical", SpineDrawableTemplate.class);
        final SpineDrawable scrollbarVerticalDrawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        ScrollPane scrollPane = selectBox.getScrollPane();
        scrollPane.setStyle(new ScrollPane.ScrollPaneStyle(scrollPane.getStyle()));
        scrollPane.getStyle().vScrollKnob = scrollbarVerticalDrawable;
        
        selectBox.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                    int pointer, int button) {
                if (selectBox.getList().isTouchable()) {
                    selectBoxDrawable.getAnimationState().setAnimation(0, "up-to-down", false);
                    scrollbarVerticalDrawable.getAnimationState().setAnimation(0, "show", false);
                    scrollbarVerticalDrawable.getAnimationState().addAnimation(0, "scrollbar", false, 0);
                    selectBoxDrawable.getSkeleton().updateWorldTransform();
                    selectBoxDrawable.getAnimationState().apply(selectBoxDrawable.getSkeleton());
                } else {
                    selectBoxDrawable.getAnimationState().setAnimation(0, "down-to-up", false);
                    scrollbarVerticalDrawable.getAnimationState().setAnimation(0, "hide", false);
                    scrollbarVerticalDrawable.getAnimationState().addAnimation(0, "hidden", false, 0);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                selectBoxDrawable.getAnimationState().setAnimation(0, "down-to-up", false);
                scrollbarVerticalDrawable.getAnimationState().setAnimation(0, "scrollbar", false);
                scrollbarVerticalDrawable.getAnimationState().addAnimation(0, "hide", false, 0);
                scrollbarVerticalDrawable.getAnimationState().addAnimation(0, "hidden", false, 0);
            }
        });
        
        window.row();
        Table table = new Table();
        table.defaults().space(20.0f).left();
        window.add(table);
        
        template = skin.get("check-box", SpineDrawableTemplate.class);
        final SpineDrawable checkBox1Drawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        final CheckBox checkBox1 = new CheckBox("Check 1", skin);
        checkBox1.setStyle(new CheckBox.CheckBoxStyle(checkBox1.getStyle()));
        checkBox1.getStyle().checkboxOn = checkBox1Drawable;
        checkBox1.getStyle().checkboxOff = checkBox1Drawable;
        checkBox1Drawable.getAnimationState().setAnimation(0, "invisible", false);
        checkBox1Drawable.getAnimationState().apply(checkBox1Drawable.getSkeleton());
        checkBox1.getLabel().setColor(1, 1, 1, 0);
        checkBox1.setChecked(true);
        table.add(checkBox1);
        
        checkBox1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (checkBox1.isChecked()) {
                    checkBox1Drawable.getAnimationState().setAnimation(0, "square to check", false);
                } else {
                    checkBox1Drawable.getAnimationState().setAnimation(0, "check to square", false);
                }
            }
            
        });
        
        ButtonGroup buttonGroup = new ButtonGroup();
        
        template = skin.get("radio", SpineDrawableTemplate.class);
        final SpineDrawable radio1Drawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        final CheckBox radio1 = new CheckBox("Radio 1", skin);
        radio1.setStyle(new CheckBox.CheckBoxStyle(radio1.getStyle()));
        radio1.getStyle().checkboxOn = radio1Drawable;
        radio1.getStyle().checkboxOff = radio1Drawable;
        radio1Drawable.getAnimationState().setAnimation(0, "invisible", false);
        radio1Drawable.getAnimationState().apply(radio1Drawable.getSkeleton());
        radio1.getLabel().setColor(1, 1, 1, 0);
        buttonGroup.add(radio1);
        table.add(radio1);
        
        radio1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (radio1.isChecked()) {
                    radio1Drawable.getAnimationState().setAnimation(0, "unselected to selected", false);
                } else {
                    radio1Drawable.getAnimationState().setAnimation(0, "selected to unselected", false);
                }
            }
            
        });
        
        table.row();
        template = skin.get("check-box", SpineDrawableTemplate.class);
        final SpineDrawable checkBox2Drawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        final CheckBox checkBox2 = new CheckBox("Czech 2", skin);
        checkBox2.setStyle(new CheckBox.CheckBoxStyle(checkBox2.getStyle()));
        checkBox2.getStyle().checkboxOn = checkBox2Drawable;
        checkBox2.getStyle().checkboxOff = checkBox2Drawable;
        checkBox2Drawable.getAnimationState().setAnimation(0, "invisible", false);
        checkBox2Drawable.getAnimationState().apply(checkBox2Drawable.getSkeleton());
        checkBox2.getLabel().setColor(1, 1, 1, 0);
        table.add(checkBox2);
        
        checkBox2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (checkBox2.isChecked()) {
                    checkBox2Drawable.getAnimationState().setAnimation(0, "square to check", false);
                } else {
                    checkBox2Drawable.getAnimationState().setAnimation(0, "check to square", false);
                }
            }
            
        });
        
        template = skin.get("radio", SpineDrawableTemplate.class);
        final SpineDrawable radio2Drawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        final CheckBox radio2 = new CheckBox("CD 2", skin);
        radio2.setStyle(new CheckBox.CheckBoxStyle(radio2.getStyle()));
        radio2.getStyle().checkboxOn = radio2Drawable;
        radio2.getStyle().checkboxOff = radio2Drawable;
        radio2Drawable.getAnimationState().setAnimation(0, "invisible", false);
        radio2Drawable.getAnimationState().apply(radio2Drawable.getSkeleton());
        radio2.getLabel().setColor(1, 1, 1, 0);
        buttonGroup.add(radio2);
        table.add(radio2);
        
        radio2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (radio2.isChecked()) {
                    radio2Drawable.getAnimationState().setAnimation(0, "unselected to selected", false);
                } else {
                    radio2Drawable.getAnimationState().setAnimation(0, "selected to unselected", false);
                }
            }
            
        });
        
        table.row();
        template = skin.get("check-box", SpineDrawableTemplate.class);
        final SpineDrawable checkBox3Drawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        final CheckBox checkBox3 = new CheckBox("Cheque 3", skin);
        checkBox3.setStyle(new CheckBox.CheckBoxStyle(checkBox3.getStyle()));
        checkBox3.getStyle().checkboxOn = checkBox3Drawable;
        checkBox3.getStyle().checkboxOff = checkBox3Drawable;
        checkBox3Drawable.getAnimationState().setAnimation(0, "invisible", false);
        checkBox3Drawable.getAnimationState().apply(checkBox3Drawable.getSkeleton());
        checkBox3.getLabel().setColor(1, 1, 1, 0);
        table.add(checkBox3);
        
        checkBox3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (checkBox3.isChecked()) {
                    checkBox3Drawable.getAnimationState().setAnimation(0, "square to check", false);
                } else {
                    checkBox3Drawable.getAnimationState().setAnimation(0, "check to square", false);
                }
            }
            
        });
        
        template = skin.get("radio", SpineDrawableTemplate.class);
        final SpineDrawable radio3Drawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        final CheckBox radio3 = new CheckBox("MP3 3", skin);
        radio3.setStyle(new CheckBox.CheckBoxStyle(radio3.getStyle()));
        radio3.getStyle().checkboxOn = radio3Drawable;
        radio3.getStyle().checkboxOff = radio3Drawable;
        radio3Drawable.getAnimationState().setAnimation(0, "invisible", false);
        radio3Drawable.getAnimationState().apply(radio3Drawable.getSkeleton());
        radio3.getLabel().setColor(1, 1, 1, 0);
        buttonGroup.add(radio3);
        table.add(radio3);
        
        radio3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (radio3.isChecked()) {
                    radio3Drawable.getAnimationState().setAnimation(0, "unselected to selected", false);
                } else {
                    radio3Drawable.getAnimationState().setAnimation(0, "selected to unselected", false);
                }
            }
            
        });
        
        window.row();
        table = new Table();
        table.defaults().space(10.0f);
        window.add(table);
        
        final Label label1 = new Label("Name: ", skin);
        label1.setColor(1, 1, 1, 0);
        table.add(label1).right();
        
        template = skin.get("textfield", SpineDrawableTemplate.class);
        final SpineDrawable textField1Drawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        final TextField textField1 = new TextField("", skin);
        textField1.setStyle(new TextField.TextFieldStyle(textField1.getStyle()));
        textField1.getStyle().background = textField1Drawable;
        textField1Drawable.getAnimationState().setAnimation(0, "invisible", false);
        textField1Drawable.getAnimationState().apply(textField1Drawable.getSkeleton());
        table.add(textField1).width(250.0f);
        
        textField1.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusListener.FocusEvent event,
                    Actor actor, boolean focused) {
                super.keyboardFocusChanged(event, actor, focused);
                if (focused) {
                    textField1Drawable.getAnimationState().setAnimation(0, "bounce", false);
                }
            }
        });
        
        template = skin.get("textfield", SpineDrawableTemplate.class);
        final SpineDrawable textField2Drawable = new SpineDrawable(skeletonJson, skeletonRenderer, template);
        
        table.row();
        
        final Label label2 = new Label("Password: ", skin);
        label2.setColor(1, 1, 1, 0);
        table.add(label2).right();
        
        final TextField textField2 = new TextField("", skin);
        textField2.setStyle(new TextField.TextFieldStyle(textField2.getStyle()));
        textField2.getStyle().background = textField2Drawable;
        textField2Drawable.getAnimationState().setAnimation(0, "invisible", false);
        textField2Drawable.getAnimationState().apply(textField2Drawable.getSkeleton());
        textField2.setPasswordMode(true);
        textField2.setPasswordCharacter('•');
        table.add(textField2).width(250.0f);
        
        textField2.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusListener.FocusEvent event,
                    Actor actor, boolean focused) {
                super.keyboardFocusChanged(event, actor, focused);
                if (focused) {
                    textField2Drawable.getAnimationState().setAnimation(0, "bounce", false);
                }
            }
        });
        
        //fade in widgets and text labels
        windowDrawable.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equals("fade-in-text")) {
                    window.getTitleLabel().addAction(Actions.alpha(1.0f, .25f));
                    
                    textButton3.addAction(Actions.delay(.5f, new Action() {
                        @Override
                        public boolean act(float delta) {
                            textButton3Drawable.getAnimationState().setAnimation(0, "fade in", false);
                            return true;
                        }
                    }));
                    
                    textButton2.addAction(Actions.delay(1.0f, new Action() {
                        @Override
                        public boolean act(float delta) {
                            textButton2Drawable.getAnimationState().setAnimation(0, "fade in", false);
                            return true;
                        }
                    }));
                    
                    textButton1.addAction(Actions.delay(1.5f, new Action() {
                        @Override
                        public boolean act(float delta) {
                            textButton1Drawable.getAnimationState().setAnimation(0, "fade in", false);
                            return true;
                        }
                    }));
                }
            }
        });
        
        textButton1Drawable.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equals("fade-in-text")) {
                    textButton1.getLabel().addAction(Actions.alpha(1.0f, .25f));
                    
                    selectBox.addAction(Actions.delay(.5f, new Action() {
                        @Override
                        public boolean act(float delta) {
                            selectBoxDrawable.getAnimationState().setAnimation(0, "show", false);
                            return true;
                        }
                    }));
                }
            }
            
        });
        
        textButton2Drawable.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equals("fade-in-text")) {
                    textButton2.getLabel().addAction(Actions.alpha(1.0f, .25f));
                }
            }
        });
        
        textButton3Drawable.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equals("fade-in-text")) {
                    textButton3.getLabel().addAction(Actions.alpha(1.0f, .25f));
                }
            }
        });
        
        selectBoxDrawable.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equals("fade-in-text")) {
                    selectBox.addAction(new Action() {
                        @Override
                        public boolean act(float delta) {
                            selectBox.getStyle().fontColor.a += delta / .25f;
                            
                            if (selectBox.getStyle().fontColor.a >= 1.0f) {
                                selectBox.getStyle().fontColor.a = 1.0f;
                                
                                return true;
                            }
                            return false;
                        }
                    });
                    
                    checkBox1.addAction(Actions.delay(.5f, new Action() {
                        @Override
                        public boolean act(float delta) {
                            checkBox1Drawable.getAnimationState().setAnimation(0, "open check", false);
                            return true;
                        }
                    }));
                    
                    checkBox2.addAction(Actions.delay(1.0f, new Action() {
                        @Override
                        public boolean act(float delta) {
                            checkBox2Drawable.getAnimationState().setAnimation(0, "open square", false);
                            return true;
                        }
                    }));
                    
                    checkBox3.addAction(Actions.delay(1.5f, new Action() {
                        @Override
                        public boolean act(float delta) {
                            checkBox3Drawable.getAnimationState().setAnimation(0, "open square", false);
                            return true;
                        }
                    }));
                }
            }
            
        });
        
        checkBox1Drawable.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equals("fade-in-text")) {
                    checkBox1.getLabel().addAction(Actions.alpha(1.0f, .25f));
                }
            }
            
        });
        
        checkBox2Drawable.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equals("fade-in-text")) {
                    checkBox2.getLabel().addAction(Actions.alpha(1.0f, .25f));
                }
            }
            
        });
        
        checkBox3Drawable.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equals("fade-in-text")) {
                    checkBox3.getLabel().addAction(Actions.alpha(1.0f, .25f));
                    
                    radio1.addAction(Actions.delay(.5f, new Action() {
                        @Override
                        public boolean act(float delta) {
                            radio1Drawable.getAnimationState().setAnimation(0, "show selected", false);
                            return true;
                        }
                    }));
                    
                    radio2.addAction(Actions.delay(1.0f, new Action() {
                        @Override
                        public boolean act(float delta) {
                            radio2Drawable.getAnimationState().setAnimation(0, "show unselected", false);
                            return true;
                        }
                    }));
                    
                    radio3.addAction(Actions.delay(1.5f, new Action() {
                        @Override
                        public boolean act(float delta) {
                            radio3Drawable.getAnimationState().setAnimation(0, "show unselected", false);
                            return true;
                        }
                    }));
                }
            }
            
        });
        
        radio1Drawable.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equals("fade-in-text")) {
                    radio1.getLabel().addAction(Actions.alpha(1.0f, .25f));
                }
            }
            
        });
        
        radio2Drawable.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equals("fade-in-text")) {
                    radio2.getLabel().addAction(Actions.alpha(1.0f, .25f));
                }
            }
        });
        
        radio3Drawable.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equals("fade-in-text")) {
                    radio3.getLabel().addAction(Actions.alpha(1.0f, .25f));
                    
                    label1.addAction(Actions.delay(.5f, Actions.alpha(1.0f, .25f)));
                    
                    textField1.addAction(Actions.delay(1.0f, new Action() {
                        @Override
                        public boolean act(float delta) {
                            textField1Drawable.getAnimationState().setAnimation(0, "show", false);
                            return true;
                        }
                    }));
                }
            }
            
        });
        
        textField1Drawable.getAnimationState().addListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equals("fade-in-text")) {
                    label2.addAction(Actions.delay(.5f, Actions.alpha(1.0f, .25f)));
                    
                    textField2.addAction(Actions.delay(1.0f, new Action() {
                        @Override
                        public boolean act(float delta) {
                            textField2Drawable.getAnimationState().setAnimation(0, "show", false);
                            return true;
                        }
                    }));
                }
            }
            
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0 / 255.0f, 14 / 255.0f, 20 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
        
        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            dispose();
            create();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public SplashWorker getSplashWorker() {
        return splashWorker;
    }

    public void setSplashWorker(SplashWorker splashWorker) {
        this.splashWorker = splashWorker;
    }
}
