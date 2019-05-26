package com.mycompany.starbattle;
import com.badlogic.gdx.*;
import com.badlogic.gdx.files.*;
import java.io.*;
import com.badlogic.gdx.graphics.profiling.*;
import com.badlogic.gdx.graphics.g2d.*;
public class LogCat extends OutputStream
{
	private OutputStream out1,out2;
	public void write(int p1)throws IOException
	{
		out1.write(p1);
		out2.write(p1);
	}
	private LogCat(OutputStream out1,OutputStream out2)
	{
		this.out1=out1;
		this.out2=out2;
	}
	public static void begin()
	{
		final Thread.UncaughtExceptionHandler handler=Thread.getDefaultUncaughtExceptionHandler();
		if(!(handler instanceof LogCat))
		{
			new Thread.UncaughtExceptionHandler()
			{
				public void uncaughtException(Thread t,Throwable errore)
				{
					System.err.println("FATAL EXCEPTION");
					errore.printStackTrace();
					handler.uncaughtException(t,errore);
				}
			};
			FileHandle fh=Gdx.files.external("AppProjects/log.txt");
			if(fh.exists())System.setOut(new PrintStream(new LogCat(fh.write(false),System.out)));
			fh=Gdx.files.external("AppProjects/errori.txt");
			if(fh.exists())System.setErr(new PrintStream(new LogCat(fh.write(false),System.err)));
			GLProfiler.enable();
		}
	}
	private static long frame;
	private static int fps;
	public static void log()
	{
		fps++;
		long tempo=System.currentTimeMillis();
		if(frame!=0)
		{
			if(tempo>=frame)
			{
				frame=tempo+1000;
				System.out.println("FPS:"+fps+" RAM:"+((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1024*1024))+"/"+((Gdx.graphics.getWidth()+Gdx.graphics.getHeight())*16/(240+320))+" CALLS:"+GLProfiler.calls+" DRAW CALLS:"+GLProfiler.drawCalls+" SHADER SWITCHES:"+GLProfiler.shaderSwitches+" TEXTURE BINDINGS:"+GLProfiler.textureBindings+" VERTEX COUNT:"+GLProfiler.vertexCount.total);
				GLProfiler.reset();
				fps=0;
			}
		}
		else frame=tempo+1000;
	}
}
