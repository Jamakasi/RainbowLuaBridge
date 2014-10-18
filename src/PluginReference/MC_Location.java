package PluginReference;

import java.io.Serializable;

/** 
 * Represents a Minecraft location.
 */ 			
public class MC_Location implements Serializable
{
	  public int dimension;
	  public double x;
	  public double y;
	  public double z;
	  public float yaw;
	  public float pitch;

	  public MC_Location(double x, double y, double z, int dimension, float yaw, float pitch)
	  {
	    this.dimension = dimension;
	    this.x = x;
	    this.y = y;
	    this.z = z;
	    this.pitch = pitch;
	    this.yaw = yaw;
	  }
	  public MC_Location(MC_Location locToCopy)
	  {
	    this.dimension = locToCopy.dimension;
	    this.x = locToCopy.x;
	    this.y = locToCopy.y;
	    this.z = locToCopy.z;
	    this.pitch = locToCopy.pitch;
	    this.yaw = locToCopy.yaw;
	  }
	  
	  public String toString()
	  {
		  String strBase = String.format("(%d, %d, %d)", (int)x, (int)y, (int)z);
		  if(dimension == 0) return strBase;
		  String worldName = "Dimension_" + dimension;
		  if(dimension == -1) worldName = "Nether";
		  if(dimension == 1) worldName = "TheEnd";
		  return worldName + strBase;
	  }

		 /** 
	     * Converts Entity Location X-coordinate to Block Location
	     * 
	     * @return Block X-Coordinate
	     */ 			
	  public int getBlockX()
	  {
		  if(x >= 0.0) return (int)x;
		  return -1 + (int)(x);
	  }
		 /** 
	     * Converts Entity Location Y-coordinate to Block Location
	     * 
	     * @return Block Y-Coordinate
	     */ 			
	  public int getBlockY()
	  {
		  if(y >= 0.0) return (int)y;
		  return -1 + (int)(y);
	  }
		 /** 
	     * Converts Entity Location Z-coordinate to Block Location
	     * 
	     * @return Block Z-Coordinate
	     */ 			
	  public int getBlockZ()
	  {
		  if(z >= 0.0) return (int)z;
		  return -1 + (int)(z);
	  }
	  
		 /** 
	     * Check if location is equal (same x, y, z, dimension, yaw, pitch)
	     * 
	     * @param loc Location
	     * @return True if same, False otherwise 
	     */ 			
	  public boolean equals(MC_Location loc) {
		  if(x != loc.x) return false;
		  if(y != loc.y) return false;
		  if(z != loc.z) return false;
		  if(dimension != loc.dimension) return false;
		  if(Math.abs(yaw - loc.yaw) > 0.0001) return false;
		  if(Math.abs(pitch - loc.pitch) > 0.0001) return false;
		  return true;
	  }
	   
		 /** 
	     * Compute distance from another location
	     * 
	     * @param loc Location to compute distance to
	     * @return Euclidean Distance 
	     */ 			
	  public double distanceTo(MC_Location loc)
	  {
		  // If dimensions are different, instead of throwing an exception just treat different dimensions as 'very far away'
		  if(loc.dimension != dimension) return Double.MAX_VALUE/2; // don't need full max value so reducing so no basic caller wrap-around.
		  
		  // Pythagoras...
		  double dx = x - loc.x;
		  double dy = y - loc.y;
		  double dz = z - loc.z;
		  return Math.sqrt(dx*dx + dy*dy + dz*dz);
	  }
	  
		 /** 
	     * Get Location at direction from this location
	     * 
	     * @param dir Direction
	     * @return Location at specified Direction 
	     */ 			
	  public MC_Location getLocationAtDirection(MC_DirectionNESWUD dir)
	  {
		  MC_Location loc = new MC_Location(this);
			
			if(dir == MC_DirectionNESWUD.NORTH) loc.z--; 
			if(dir == MC_DirectionNESWUD.SOUTH) loc.z++;
			if(dir == MC_DirectionNESWUD.WEST) loc.x--;
			if(dir == MC_DirectionNESWUD.EAST) loc.x++;
			if(dir == MC_DirectionNESWUD.UP) loc.y++; 
			if(dir == MC_DirectionNESWUD.DOWN) loc.y--;

			return loc;
		}
	  
}
