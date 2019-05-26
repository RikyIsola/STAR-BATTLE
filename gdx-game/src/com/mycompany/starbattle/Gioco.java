package com.mycompany.starbattle;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.viewport.*;
import java.util.*;
public class Gioco extends Stage implements Screen
{
	private int livello;
	private Nave[]nemici={Nave.klingon,Nave.enterprise,Nave.enterprise2,Nave.romulani,Nave.borg};
	private Image nave,nemico,naveS,nemicoS;
	private Nave naveNave,nemicoNave;
	private ArrayList<Image>proiettili=new ArrayList<Image>();
	private float tempo,tempoNemico,x,obiettivoX,nas,nes;
	private ProgressBar naveScafo,naveScudi,nemicoScafo,nemicoScudi;
	private Label messaggio;
	public Gioco(Nave personaggio)
	{
		super(new StretchViewport(500,500),MyGdxGame.game.b);
		naveNave=personaggio;
		nemicoNave=nemici[livello];
		MyGdxGame.game.asset.finishLoading();
		addListener(input(null));
		
		Image image=new Image(new SpriteDrawable(new Sprite(MyGdxGame.game.asset.get("sfondo.png",Texture.class))));
		addActor(image);
		image.setBounds(0,0,500,500);
		
		Label label=new Label("HULL:",new Label.LabelStyle(MyGdxGame.game.bf,Color.WHITE));
		addActor(label);
		label.setColor(Color.BLUE);
		label.setBounds(0,475,100,25);
		label.setAlignment(Align.center);
		
		label=new Label("SHIELD:",new Label.LabelStyle(MyGdxGame.game.bf,Color.WHITE));
		addActor(label);
		label.setColor(Color.BLUE);
		label.setBounds(0,450,100,25);
		label.setAlignment(Align.center);
		
		label=new Label("ENEMY HULL:",new Label.LabelStyle(MyGdxGame.game.bf,Color.WHITE));
		addActor(label);
		label.setColor(Color.BLUE);
		label.setBounds(275,475,100,25);
		label.setAlignment(Align.right);

		label=new Label("ENEMY SHIELD:",new Label.LabelStyle(MyGdxGame.game.bf,Color.WHITE));
		addActor(label);
		label.setColor(Color.BLUE);
		label.setBounds(275,450,100,25);
		label.setAlignment(Align.right);
		
		label=new Label("",new Label.LabelStyle(MyGdxGame.game.bf,Color.WHITE));
		addActor(label);
		label.setColor(Color.BLUE);
		label.setBounds(0,200,500,100);
		label.setAlignment(Align.center);
		messaggio=label;

		ProgressBar bar=new ProgressBar(0,naveNave.scudi,0.001f,false,new ProgressBar.ProgressBarStyle(MyGdxGame.game.skin.newDrawable("white",Color.RED),null));
		bar.setBounds(100,456.25f,naveNave.scudi*1.25f,12.5f);
		bar.getStyle().background.setMinHeight(bar.getHeight());
		bar.getStyle().knobBefore=MyGdxGame.game.skin.newDrawable("white",Color.GREEN);
		bar.getStyle().knobBefore.setMinHeight(bar.getHeight());
		bar.setAnimateDuration(0.5f);
		bar.setValue(100);
		addActor(bar);
		naveScudi=bar;
		
		bar=new ProgressBar(0,naveNave.scafo,0.001f,false,new ProgressBar.ProgressBarStyle(MyGdxGame.game.skin.newDrawable("white",Color.RED),null));
		bar.setBounds(100,481.25f,naveNave.scafo*1.25f,12.5f);
		bar.getStyle().background.setMinHeight(bar.getHeight());
		bar.getStyle().knobBefore=MyGdxGame.game.skin.newDrawable("white",Color.GREEN);
		bar.getStyle().knobBefore.setMinHeight(bar.getHeight());
		bar.setAnimateDuration(0.5f);
		bar.setValue(100);
		addActor(bar);
		naveScafo=bar;
		
		bar=new ProgressBar(0,nemicoNave.scudi,0.001f,false,new ProgressBar.ProgressBarStyle(MyGdxGame.game.skin.newDrawable("white",Color.RED),null));
		bar.setBounds(375,456.25f,nemicoNave.scudi*1.25f,12.5f);
		bar.getStyle().background.setMinHeight(bar.getHeight());
		bar.getStyle().knobBefore=MyGdxGame.game.skin.newDrawable("white",Color.GREEN);
		bar.getStyle().knobBefore.setMinHeight(bar.getHeight());
		bar.setAnimateDuration(0.5f);
		bar.setValue(100);
		addActor(bar);
		nemicoScudi=bar;

		bar=new ProgressBar(0,nemicoNave.scafo,0.001f,false,new ProgressBar.ProgressBarStyle(MyGdxGame.game.skin.newDrawable("white",Color.RED),null));
		bar.setBounds(375,481.25f,nemicoNave.scafo*1.25f,12.5f);
		bar.getStyle().background.setMinHeight(bar.getHeight());
		bar.getStyle().knobBefore=MyGdxGame.game.skin.newDrawable("white",Color.GREEN);
		bar.getStyle().knobBefore.setMinHeight(bar.getHeight());
		bar.setAnimateDuration(0.5f);
		bar.setValue(100);
		addActor(bar);
		nemicoScafo=bar;
		
		image=new Image(new SpriteDrawable(new Sprite(MyGdxGame.game.asset.get(naveNave.img,Texture.class))));
		addActor(image);
		image.setRotation(180);
		image.setBounds(225,0,50,50);
		image.setOrigin(image.getWidth()/2,image.getHeight()/2);
		nave=image;
		x=image.getX();
		
		image=new Image(new SpriteDrawable(new Sprite(MyGdxGame.game.asset.get(nemicoNave.img,Texture.class))));
		addActor(image);
		image.setBounds(225,450,50,50);
		nemico=image;
		obiettivoX=image.getX();
		
		image=new Image(new SpriteDrawable(new Sprite(MyGdxGame.game.asset.get("scudi.png",Texture.class))));
		addActor(image);
		image.setBounds(225,450,50,50);
		image.addListener(input(image));
		((SpriteDrawable)image.getDrawable()).getSprite().setAlpha(0);
		nemicoS=image;
		
		image=new Image(new SpriteDrawable(new Sprite(MyGdxGame.game.asset.get("scudi.png",Texture.class))));
		addActor(image);
		image.setBounds(225,0,50,50);
		image.addListener(input(image));
		((SpriteDrawable)image.getDrawable()).getSprite().setAlpha(0);
		naveS=image;
		
		if(MyGdxGame.debugLine)setDebugAll(true);
		MyGdxGame.game.setScreen(this);
	}
	public void render(float delta)
	{
		if(nas>0)
		{
			nas-=delta;
			if(nas<0)nas=0;
			((SpriteDrawable)naveS.getDrawable()).getSprite().setAlpha(nas);
		}
		if(nes>0)
		{
			nes-=delta;
			if(nes<0)nes=0;
			((SpriteDrawable)nemicoS.getDrawable()).getSprite().setAlpha(nes);
		}
		if(naveScafo.getValue()>0)
		{
			if(tempo>0)tempo-=delta;
			float vel=naveNave.velocita*delta*naveScafo.getValue()/naveScafo.getMaxValue();
			if(Math.abs(x-nave.getCenterX())>vel)
			{
				if(x<nave.getCenterX())nave.setX(nave.getX()-vel);
				else if(x>nave.getCenterX())nave.setX(nave.getX()+vel);
				if(nave.getX()+nave.getWidth()>getViewport().getWorldWidth())nave.setX(getViewport().getWorldWidth()-nave.getWidth());
				else if(nave.getX()<0)nave.setX(0);
			}
			naveScafo.setValue(naveScafo.getValue()+naveNave.ripScafo*delta);
			naveScudi.setValue(naveScudi.getValue()+naveNave.ripScudi*delta);
		}
		
		if(nemicoScafo.getValue()>0)
		{
			if(tempoNemico>0)tempoNemico-=delta;
			else
			{
				tempoNemico=nemicoNave.sparo;
				Image image=new Image(MyGdxGame.game.asset.get(nemicoNave.pImg,Texture.class));
				addActor(image);
				image.setRotation(180);
				image.setBounds(nemico.getX()+nemico.getWidth()/4,nemico.getY()-nemico.getHeight(),nemico.getWidth()/2,nemico.getHeight());
				image.setOrigin(image.getWidth()/2,image.getHeight()/2);
				MoveToAction mta=new MoveToAction();
				mta.setPosition(image.getX(),-image.getHeight());
				mta.setDuration(nemicoNave.tProiettili);
				image.addAction(mta);
				proiettili.add(image);
				MyGdxGame.game.asset.get("siluro.ogg",Sound.class).play();
			}
			float vel=nemicoNave.velocita*delta*nemicoScafo.getValue()/nemicoScafo.getMaxValue();
			if(Math.abs(nemico.getX()-obiettivoX)<vel)obiettivoX=MyGdxGame.game.r.nextInt((int)(getViewport().getWorldWidth()-nemico.getWidth()));
			else
			{
				if(obiettivoX<nemico.getX())nemico.setX(nemico.getX()-vel);
				else nemico.setX(nemico.getX()+vel);
			}
			nemicoScafo.setValue(nemicoScafo.getValue()+nemicoNave.ripScafo*delta);
			nemicoScudi.setValue(nemicoScudi.getValue()+nemicoNave.ripScudi*delta);
		}
		
		for(int a=0;a<proiettili.size();a++)
		{
			Image img=proiettili.get(a);
			if(img.getY()>nemico.getY()-nemico.getHeight()&&img.getX()<nemico.getX()+nemico.getWidth()&&img.getX()+img.getWidth()>nemico.getX())
			{
				img.remove();
				proiettili.remove(img);
				a--;
				final float n=naveNave.attacco;
				boolean scudi=true;
				for(int b=0;b<n;b++)
				{
					if(nemicoScudi.getValue()>0)nemicoScudi.setValue(nemicoScudi.getValue()-1);
					else
					{
						nemicoScafo.setValue(nemicoScafo.getValue()-1);
						scudi=false;
					}
				}
				if(scudi)
				{
					nes=1;
					MyGdxGame.game.asset.get("scudicolpiti.ogg",Sound.class).play();
				}
				else if(nemicoScafo.getValue()>0)MyGdxGame.game.asset.get("scafocolpito.ogg",Sound.class).play();
				else
				{
					scudi=false;
					messaggio.setText("TOUCH THE ENEMY TO START THE NEXT LEVEL");
					((SpriteDrawable)nemico.getDrawable()).getSprite().setTexture(MyGdxGame.game.asset.get("esplosione.png",Texture.class));
					break;
				}
			}
			else if(img.getY()<nemico.getHeight()&&img.getX()<nave.getX()+nave.getWidth()&&img.getX()+img.getWidth()>nave.getX())
			{
				img.remove();
				proiettili.remove(img);
				a--;
				final float n=nemicoNave.attacco;
				boolean scudi=true;
				for(int b=0;b<n;b++)
				{
					if(naveScudi.getValue()>0)naveScudi.setValue(naveScudi.getValue()-1);
					else
					{
						naveScafo.setValue(naveScafo.getValue()-1);
						scudi=false;
					}
				}
				if(scudi)
				{
					nas=1;
					MyGdxGame.game.asset.get("scudicolpiti.ogg",Sound.class).play();
				}
				else if(naveScafo.getValue()>0)MyGdxGame.game.asset.get("scafocolpito.ogg",Sound.class).play();
				else
				{
					messaggio.setText("TOUCH YOUR SHIP TO RESTART THE GAME");
					scudi=false;
					((SpriteDrawable)nave.getDrawable()).getSprite().setTexture(MyGdxGame.game.asset.get("esplosione.png",Texture.class));
					break;
				}
			}
			else if(img.getY()==getViewport().getWorldHeight()||img.getY()==-img.getHeight())
			{
				img.remove();
				proiettili.remove(img);
				a--;
			}
		}
		
		naveS.setX(nave.getX());
		nemicoS.setX(nemico.getX());
		act(delta);
		draw();
	}
	public void resize(int w,int h)
	{
		getViewport().update(w,h);
	}
	public void show()
	{
		Gdx.input.setInputProcessor(this);
	}
	public void hide()
	{
	}
	public void pause()
	{
	}
	public void resume()
	{
	}
	public void dispose()
	{
	}
	private InputListener input(final Actor actor)
	{
		return new InputListener()
		{
			public void touchDragged(InputEvent event,float x,float y,int pointer)
			{
				if(actor==null)
				{
					if(!(x>nave.getX()&&x<nave.getX()+nave.getWidth()))Gioco.this.x=x;
				}
			}
			public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
			{
				if(actor==null)
				{
					if(!(x>nave.getX()&&x<nave.getX()+nave.getWidth()))Gioco.this.x=x;
				}
				else if(actor.getY()==0)
				{
					if(naveScafo.getValue()>0)
					{
						if(tempo<=0)
						{
							tempo=naveNave.sparo;
							Image image=new Image(MyGdxGame.game.asset.get(naveNave.pImg,Texture.class));
							addActor(image);
							image.setRotation(180);
							image.setBounds(nave.getX()+nave.getWidth()/4,nave.getY()+nave.getHeight(),nave.getWidth()/2,nave.getHeight());
							image.setOrigin(image.getWidth()/2,image.getHeight()/2);
							MoveToAction mta=new MoveToAction();
							mta.setPosition(image.getX(),getViewport().getWorldHeight());
							mta.setDuration(naveNave.tProiettili);
							image.addAction(mta);
							proiettili.add(image);
							MyGdxGame.game.asset.get("siluro.ogg",Sound.class).play();
						}
					}
					else
					{
						new Titolo();
					}
				}
				else if(nemicoScafo.getValue()==0)
				{
					messaggio.setText("");
					livello++;
					if(livello>=nemici.length)
					{
						
					}
					else
					{
						nemicoNave=nemici[livello];
						((SpriteDrawable)nemico.getDrawable()).getSprite().setTexture(MyGdxGame.game.asset.get(nemicoNave.img,Texture.class));
						nemicoScafo.setRange(0,nemicoNave.scafo);
						nemicoScudi.setRange(0,nemicoNave.scudi);
						nemicoScafo.setWidth(nemicoNave.scafo);
						nemicoScudi.setWidth(nemicoNave.scudi);
						nemicoScafo.setValue(100);
						nemicoScudi.setValue(100);
					}
				}
				return true;
			}
		};
	}
	public boolean keyDown(int keyCode)
	{
		if(keyCode==Input.Keys.BACK)new Titolo();
		return true;
	}
}
