package com.hexotic.v2.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import com.hexotic.lib.exceptions.ResourceException;
import com.hexotic.lib.resource.Resources;
import com.hexotic.v2.console.Log;

/**
 * This is a custom "pretty" looking input box with placeholder
 * functionality for the YouTube Downloader UI
 * 
 * @author Bradley Sheets
 *
 */
public class TextFieldWithPrompt extends JTextField {

	private static final long serialVersionUID = 127422547464506328L;
	private String prompt;
	private Image confirm = null;
	private Image deny = null;

	private boolean acceptedInput = false;

	public TextFieldWithPrompt(String text, String prompt) {
		super(text);
		this.prompt = prompt;

		try {
			confirm = Resources.getInstance().getImage("status/accept.png");
			deny = Resources.getInstance().getImage("status/deny.png");
		} catch (ResourceException e1) { }
		
		this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 25));
		this.setForeground(new Color(0x424242));
		try {
			Font f = Resources.getInstance().getFont("default.ttf");
			f = f.deriveFont(17f).deriveFont(Font.BOLD);
			this.setFont(f);
		} catch (FontFormatException e) {
			Log.getInstance().error(this, "Couldn't Load Font", e);
		} catch (IOException e) {
			Log.getInstance().error(this, "Couldn't Load Font", e);
		}
	}

	public void setAccepted(boolean accepted){
		this.acceptedInput = accepted;
		this.revalidate();
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(new Color(0xffffff));
		g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 4, 4);

		g2d.setColor(new Color(0xe0e0e0));
		g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 2, 4, 4);

		Paint p = new GradientPaint(0, 0, new Color(0xd8d8d8), 0, 2, new Color(0, 0, 0, 0));
		g2d.setPaint(p);
		g2d.fillRect(0, 0, getWidth(), 2);

		if (!getText().isEmpty() && acceptedInput) {
			g2d.drawImage(confirm, getWidth() - 20, getHeight() / 2 - 8, null);
		} else if (!getText().isEmpty() && !acceptedInput) {
			g2d.drawImage(deny, getWidth() - 20, getHeight() / 2 - 8, null);
		} else {
			g2d.setColor(new Color(0x888888));
			g2d.setFont(new Font("Arial", Font.BOLD, 12));
			g2d.drawString(prompt, 5, 21);
		}
	}
}
