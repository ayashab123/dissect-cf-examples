package uk.ac.ljmu.fet.cs.cloud.examples.autoscaler;

import java.util.ArrayList;
import java.util.Iterator;

import hu.mta.sztaki.lpds.cloud.simulator.iaas.IaaSService;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.VirtualMachine;

public class VMScaler extends VirtualInfrastructure{
	// extending to Virtual Infrastructure 
	
	private String applicationName = "";
	
	
	

	public VMScaler(IaaSService cloud) {
		super(cloud);

	}

	@Override
	public void tick(long fires) {
		if(applicationName.equals("")) {
			
		
		final Iterator<String> kinds = vmSetPerKind.keySet().iterator();
		while(kinds.hasNext()) {
			final String kind = kinds.next();
			final ArrayList<VirtualMachine> vmset = vmSetPerKind.get(kind);
			// Determining if new VM is needed  
			
			if(vmset.isEmpty()) {
				// IF the VM is empty then request a new VM
				requestVM(kind);
				
				//Remember application
				applicationName = kind;
				//System.out.println(applicationName);
			}else {
				
				ArrayList<VirtualMachine> investigatedList = new ArrayList<VirtualMachine>();
				for (VirtualMachine vm : vmset) {
					
					if(vm.getState() == VirtualMachine.State.RUNNING) {
						if(vm.underProcessing.isEmpty() && vm.toBeAdded.isEmpty()) {
							investigatedList.add(vm);
						}
					}else {
						
					}
					
				}
				// check to see if VM list is empty if so destroy all VMs
				if(!investigatedList.isEmpty()) {
					for(VirtualMachine vm : investigatedList) {
						destroyVM(vm);
					}
				}
				
			}
			
			//removed first non-checkedVI
			
			
			
		}
		}else{
			final ArrayList<VirtualMachine> vmset = vmSetPerKind.get(applicationName);
			requestVM(applicationName);
			if(vmset.size() >= 3) {
				applicationName = "";
			}
		}
	}

	
	
	
	
	

	
	
}