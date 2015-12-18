package dk.itu.oop.peg.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class SerializationManager<T>
{
	private final static String projectPath = System.getProperty("user.dir" );
	
	public void SerializeBoard(String name, T toSerialize) throws IOException
	{
		try (FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(projectPath, name).toString());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						fileOutputStream);)
		{
			objectOutputStream.writeObject(toSerialize);
		}
	}

	@SuppressWarnings("unchecked")
	public T DeserializeBoard(String name) throws IOException,
			ClassNotFoundException
	{
		try (FileInputStream fileInputStream = new FileInputStream(Paths.get(projectPath, name).toString());
				ObjectInputStream objectInputStream = new ObjectInputStream(
						fileInputStream);)
		{
			return (T) objectInputStream.readObject();
		}
	}
}
