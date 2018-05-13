package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Hello world!
 *
 */
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.swing.JOptionPane;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;

//发送邮件工具类
public class SendUtil {

	private Session session = null;

	public static Session getSession(final String email, final String pwd) {
		//
		Properties pro = new Properties();
		// 发送服务器需要身份验证
		pro.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		pro.setProperty("mail.smtp.host", "smtp.qq.com");
		// 发送邮件协议名称
		pro.setProperty("mail.transport.protocol", "smtp");
		pro.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		pro.setProperty("mail.smtp.port", "465");
		pro.setProperty("mail.smtp.ssl.enable", "true");
		// 设置环境信息
		Session session = Session.getInstance(pro, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(email, pwd);
			}
		});
		session.setDebug(true);
		return session;
	}

	public boolean sendAttach(String from, String to, List<String> filePaths) {
		// 创建邮件对象
		Message msg = new MimeMessage(session);
		MimeMultipart mmp = new MimeMultipart("mixed");// MIME消息头组合类型是mixed(html+附件)
		try {
			// 设置发件人
			msg.setFrom(new InternetAddress(from));// 从我的邮箱
													// ,服务器邮箱，这个发件人要和上面的授权的人是一个邮箱
			msg.setRecipient(RecipientType.TO, new InternetAddress(to));// 目的邮箱
			msg.setSubject("convert");
			msg.setContent("发一波书", "text/html;charset=UTF-8");
			for (String filePath : filePaths) {
				mmp.addBodyPart(getAttachedBodyPart(filePath));
			}
			msg.setContent(mmp);
			msg.saveChanges();
			// 自动close开销比较大,用sendMessage比较好,反正也没人用无所谓.
			Transport.send(msg);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	public static String doHandlerFileName(String filePath) {
		String fileName = filePath;
		if (null != filePath && !"".equals(filePath)) {
			fileName = filePath.substring(filePath.lastIndexOf("//") + 1);
		}
		return fileName;
	}

	public static MimeBodyPart getAttachedBodyPart(String filePath)
			throws MessagingException, UnsupportedEncodingException {
		MimeBodyPart attached = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(filePath);
		attached.setDataHandler(new DataHandler(fds));
		String fileName = doHandlerFileName(filePath);
		attached.setFileName(MimeUtility.encodeWord(fileName));// 处理附件文件的中文名问题
		return attached;
	}

	public void showDialog(boolean flag)
	{
		String msg = flag?"上传成功":"抱歉上传失败，请重新试一试";
		int level = flag?JOptionPane.INFORMATION_MESSAGE:JOptionPane.ERROR_MESSAGE;
		JOptionPane.showMessageDialog(null, msg,"kindle传输结果", level);
	}
	
	public static void main(String[] args) {
		SendUtil util = new SendUtil();
		Constant con = new Constant();
		ArrayList<String> books = new ArrayList<String>();
		ArrayList<String> config = con.getConfig();
		if (config.size() >= 3) {
			String kindleEmail = config.get(0);
			String sendEmail = config.get(1);
			String pwd = config.get(2);
			util.session = util.getSession(sendEmail, pwd);
			try {
				File f = new File(args[0]);
				if (f.isDirectory()) {
					String[] list = f.list();
					for (int i = 0; i < list.length; i++) {
						books.add(args[0] + "\\" + list[i]);
					}
				} else
					books.add(args[0]);
				util.sendAttach(sendEmail, kindleEmail, books);
				util.showDialog(true);
			} catch (Exception e) {
				e.printStackTrace();
				util.showDialog(false);
			}
		}

	}
}