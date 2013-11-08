import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.InstrumentPolarity
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.Group
import it.fmach.metadb.isatab.model.Project

class BootStrap {

    def init = { servletContext ->
		// create the Synapt instrument
		if (!Instrument.count()) {
			
			def pos = new InstrumentPolarity(
					name: 'untargeted positive',
					tag: 'RP_pos',
					startPattern: '1.blank-1.STDmix-4.QC',
					repeatPattern: '6.sample-1.QC',
					endPattern: '1.STDmix-1.blank'
				)
			
			def neg = new InstrumentPolarity(
				name: 'untargeted negative',
				tag: 'RP_neg',
				startPattern: '1.blank-1.STDmix-4.QC',
				repeatPattern: '6.sample-1.QC',
				endPattern: '1.STDmix-1.blank'
			)
			
			def polarities = [pos, neg]
			def methods = [new InstrumentMethod(name: 'untargeted', polarities: polarities)]
			
			new Instrument(name: "Synapt", metabolightsName: "SYNAPT HDMS (Waters)", methods: methods).save(failOnError: true)
		}
		
		// create groups and projects
		if(!Group.count()){
			def project = new Project(
					name: 'Wine cellar'
				)
			
		}
		def projects = [project]
		new Group(name: "Fulvio", projects:projects)
    }
	
    def destroy = {
    }
	
}
