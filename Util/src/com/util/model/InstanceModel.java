package com.util.model;

/**
 * 
 * @author PUSHPAK
 *
 */
public class InstanceModel {

	private Class<?> clazz;
	private Object[] args;

	/**
	 * Constructor for Instancemodel
	 * 
	 * @param clazz
	 *            class
	 * @param args
	 *            constructor arguments for the given class
	 */
	public InstanceModel(Class<?> clazz, Object... args) throws NullPointerException {

		if (clazz == null) {
			throw new NullPointerException("Class name cannot be null");
		}
		this.clazz = clazz;
		this.args = args;
	}

	/**
	 * Returns the class
	 * 
	 * @return
	 */
	public Class<?> getClazz() {

		return clazz;
	}

	/**
	 * Returns constructor arguments
	 * 
	 * @return
	 */
	public Object[] getArgs() {

		return args;
	}

	/**
	 * Check if two instancemodels are equal
	 * 
	 * @param model
	 * @return
	 */
	public boolean equals(Object o) {

		if (o instanceof InstanceModel) {
			InstanceModel model = (InstanceModel) o;
			if (!clazz.getName().equals(model.getClazz().getName())) {
				return false;
			}
			Object[] modelArgs = model.getArgs();
			if (args.length != modelArgs.length) {
				return false;
			}
			int index = 0;
			while (index < args.length) {
				if (!args[index].getClass().getName().equals(modelArgs[index].getClass().getName())) {
					return false;
				}
				if (!args[index].equals(modelArgs[index])) {
					return false;
				}
				index++;
			}
			return true;
		}

		return false;
	}

	/**
	 * Returns hashcode for the object
	 * 
	 * @return hashcode
	 */
	public int hashCode() {

		int hash = 0;
		int rem = 1000000007;
		hash = hash % rem + clazz.hashCode() % rem;
		for (Object arg : args) {
			hash = hash % rem + arg.hashCode() % rem;
		}

		return hash;
	}
}
