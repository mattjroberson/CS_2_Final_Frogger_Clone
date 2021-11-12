package logic;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class UpdateHandler<E> {

	private Queue<E> newObjects;
	private Queue<E> oldObjects;
	private boolean needsUpdate;
	
	public UpdateHandler() {
		newObjects = new LinkedList<E>();
		oldObjects = new LinkedList<E>();
		needsUpdate = false;
	}
	
	public void add(E newObject) {
		newObjects.offer(newObject);
		needsUpdate = true;
	}
	
	public void addAll(Collection<E> newObjects) {
		this.newObjects.addAll(newObjects);
		needsUpdate = true;
	}
	
	public void remove(E oldObject) {
		oldObjects.offer(oldObject);
		needsUpdate = true;
	}
	
	public boolean needsUpdate() { return needsUpdate; }
	
	public void setNeedsUpdate(boolean needsUpdate) { this.needsUpdate = needsUpdate; }
	
	public Queue<E> getNewObjects() { return newObjects; }
	
	public Queue<E> getOldObjects() { return oldObjects; }
}
