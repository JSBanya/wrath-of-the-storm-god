package jb.gui;

import java.util.ArrayList;
import jb.graphics.GameText;
import jb.graphics.Screen;

public class GraphicsInterface
{
	/*
	 	Handles grouping of gui components
	 */
	
	private ArrayList<SelectionComponent> selectionComponents = new ArrayList<SelectionComponent>();
	private int selectionComponents_indexSelected = 0;
	
	private ArrayList<FillBarComponent> fillBarComponents = new ArrayList<FillBarComponent>();
	private ArrayList<BackgroundComponent> backgroundComponents = new ArrayList<BackgroundComponent>(); 
	private ArrayList<ImageComponent> imageComponents = new ArrayList<ImageComponent>(); 
	private ArrayList<ButtonComponent> buttonComponents = new ArrayList<ButtonComponent>(); 
	private ArrayList<GameText> text = new ArrayList<GameText>(); 
	
	
	public void tick()
	{
		tickSelectionComponents();
	}
	
	private void tickSelectionComponents()
	{
		// Selection Components
		for(int i = 0; i < selectionComponents.size(); i++)
		{
			SelectionComponent sc = selectionComponents.get(i);
			
			sc.tick();
			
			if(sc.isSelected() && i != selectionComponents_indexSelected) // New selection has occurred
			{
				selectionComponents.get(selectionComponents_indexSelected).setSelected(false);
				selectionComponents_indexSelected = i;
			}
		}
		
		for(int i = 0; i < buttonComponents.size(); i++)
		{
			buttonComponents.get(i).tick();
		}
	}

	public void render(Screen screen)
	{
		// Background Components
		for(BackgroundComponent b : backgroundComponents)
			b.render(screen);
		
		// Selection Components
		for(SelectionComponent sc : selectionComponents)
			sc.render(screen);
		
		// Fill bars
		for(FillBarComponent f : fillBarComponents)
			f.render(screen);
		
		// Image Components
		for(ImageComponent i : imageComponents)
			i.render(screen);
		
		// Button Components
		for(ButtonComponent b : buttonComponents)
			b.render(screen);
		
		// Text
		for(GameText g : text)
			g.render(screen);
	}
	
	public void addSelectionComponent(SelectionComponent sc)
	{
		selectionComponents.add(sc);
	}
	
	public void selectSelectionComponent(int i)
	{
		if(i < selectionComponents.size() && i >= 0)
		{
			selectionComponents_indexSelected = i;
			
			for(SelectionComponent sc : selectionComponents)
				sc.setSelected(false);
			
			selectionComponents.get(i).setSelected(true);
		}
	}
	
	public int getSelectedComponent()
	{
		return selectionComponents_indexSelected;
	}
	
	public SelectionComponent getSelectionComponent(int index)
	{
		if(index < selectionComponents.size())
			return selectionComponents.get(index);
		else
			return null;
	}
	
	public void removeSelectionComponent(int index)
	{
		if(index < selectionComponents.size())
			selectionComponents.remove(index);
	}
	
	public void addFillBarComponent(FillBarComponent f)
	{
		fillBarComponents.add(f);
	}
	
	public FillBarComponent getFillBar(int index)
	{
		if(index < fillBarComponents.size())
			return fillBarComponents.get(index);
		else
			return null;
	}
	
	public void removeFillBar(int index)
	{
		if(index < fillBarComponents.size())
			fillBarComponents.remove(index);
	}
	
	public void addImageComponent(ImageComponent i)
	{
		imageComponents.add(i);
	}
	
	public void removeImageComponent(int index)
	{
		if(index < imageComponents.size())
			imageComponents.remove(index);
	}
	
	public ImageComponent getImageComponent(int index)
	{
		if(index < imageComponents.size())
			return imageComponents.get(index);
		else
			return null;
	}
	
	public void addBackgroundComponent(BackgroundComponent i)
	{
		backgroundComponents.add(i);
	}
	
	public void removeBackgroundComponent(int index)
	{
		if(index < backgroundComponents.size())
			backgroundComponents.remove(index);
	}
	
	public BackgroundComponent getBackgroundComponent(int index)
	{
		if(index < backgroundComponents.size())
			return backgroundComponents.get(index);
		else
			return null;
	}
	
	public void addText(GameText g)
	{
		text.add(g);
	}
	
	public void removeText(int index)
	{
		if(index < text.size())
			text.remove(index);
	}
	
	public GameText getText(int index)
	{
		if(index < text.size())
			return text.get(index);
		else
			return null;
	}
	
	public void addButtonComponent(ButtonComponent i)
	{
		buttonComponents.add(i);
	}
	
	public void removeButtonComponent(int index)
	{
		if(index < buttonComponents.size())
			buttonComponents.remove(index);
	}
	
	public ButtonComponent getButtonComponent(int index)
	{
		if(index < buttonComponents.size())
			return buttonComponents.get(index);
		else
			return null;
	}
}
