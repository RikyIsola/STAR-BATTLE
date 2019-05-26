package com.mycompany.starbattle;
public class Nave
{
	public Nave(String p,String pp,float p1,float p2,float p3,float p4,float p5,float p6,float p7,float p8)
	{
		img=p;
		pImg=pp;
		scafo=p1;
		scudi=p2;
		sparo=p3;
		velocita=p4;
		attacco=p5;
		ripScudi=p6;
		ripScafo=p7;
		tProiettili=p8;
	}
	public final String img,pImg;
	public final float scafo,scudi,sparo,velocita,attacco,ripScudi,ripScafo,tProiettili;
	public static final Nave enterprise=new Nave("enterprise.png","silurorosso.png",50,50,1,100,8,1,1,2.5f);
	public static final Nave enterprise2=new Nave("enterprise2.png","silurorosso.png",45,60,0.9f,110,9,1.1f,0.9f,2.3f);
	public static final Nave enterprise4=new Nave("enterprise4.png","silurorosso.png",40,70,0.8f,120,10,1.2f,0.8f,2f);
	public static final Nave klingon=new Nave("klingon.png","siluroverde.png",30,25,0.5f,150,5,0.5f,0.8f,2);
	public static final Nave borg=new Nave("borg.png","siluroultra.png",100,10,2,25,25,0.3f,2,5);
	public static final Nave romulani=new Nave("romulani.png","siluroblu.png",25,75,0.8f,125,10,1.5f,0.3f,3);
	public static final String[]nomi={"HULL","SHIELDS","FIRE DELAY","SPEED","POWER","SHIELD REPAIR","HULL REPAIR","TORPEDOES SLOW"};
	public static final float[]max={100,100,2,150,25,2,2,5};
	public static Nave nave(String nome)
	{
		if(enterprise.img.equals(nome))return enterprise;
		else if(klingon.img.equals(nome))return klingon;
		if(romulani.img.equals(nome))return romulani;
		else return borg;
	}
	public float valore(int n)
	{
		switch(n)
		{
			case 0:return scafo;
			case 1:return scudi;
			case 2:return sparo;
			case 3:return velocita;
			case 4:return attacco;
			case 5:return ripScudi;
			case 6:return ripScafo;
			case 7:return tProiettili;
			default:return 0;
		}
	}
}
