/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 */
@Controller
class VisitController {

	private final VisitRepository visits;

	private final PetRepository pets;

	public VisitController(VisitRepository visits, PetRepository pets) {
		this.visits = visits;
		this.pets = pets;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
	 * we always have fresh data - Since we do not use the session scope, make sure that
	 * Pet object always has an id (Even though id is not part of the form fields)
	 * @param petId
	 * @return Pet
	 */
	@ModelAttribute("visit")
	public Visit loadPetWithVisit(@PathVariable("petId") int petId, Map<String, Object> model) {
		Pet pet = this.pets.findById(petId);
		pet.setVisitsInternal(this.visits.findByPetId(petId));
		model.put("pet", pet);
		Visit visit = new Visit();
		pet.addVisit(visit);
		return visit;
	}

	// Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
	@GetMapping("/owners/*/pets/{petId}/visits/new")
	public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		return "pets/createOrUpdateVisitForm";
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		}
		else {
			this.visits.save(visit);
			return "redirect:/owners/{ownerId}";
		}
	}

	/**
	 * Trial and error mostly but I did enjoy it
	 *
	 * The most difficult part was to somehow make changes to already existing visit, overwriting it
	 * or making a new one and deleting the old one
	 * The second implementation might be safer in case of power shutoff or Internet shortage as we
	 * are still keeping the copy of old visit and we can delete it just before or after adding the altered one
	 *
	 *
	 * @author Igor Danieluk
	 *
	 * @param visitId to change the correct visit
	 * @param model   ...
	 * @param petId	  to make changes to correct pet
	 * @return		  webpage to create a new Visit
	 */
	@GetMapping("/owners/{ownerId}/pets/{petId}/visits/{visitId}/change")
	public String initChangeVisit(@PathVariable("visitId") int visitId, Map<String, Object> model, @PathVariable("petId") int petId) {
		//what it all does is finding the correct pet and its visits
		//removing the one to be changed from visible previous visits
		//on a createOrUpdateVisitFrom webpage
		Pet pet = this.pets.findById(petId);
		pet.setVisitsInternal(this.visits.findByPetId(petId));
		model.put("pet", pet);
		Visit visit = new Visit();
		for (Visit v: pet.getVisitsInternal()
		) { if (v.getId() == visitId){
			visit = v;
		}
		}
		pet.getVisitsInternal().remove(visit);
		return "pets/createOrUpdateVisitForm";

	}

	/**
	 * What I think it does, after the new Visit is made, it checks if it is correct
	 * and if yes, it replaces the id of a new one with and old visit, overwriting it
	 * and saving it to the database
	 *
	 * @author Igor Danieluk
	 *
	 * @param visit   the altered visit provided by user
	 * @param result  I am not quite sure what it does but probably checks if everything is correct
	 * @param pet     The pet that has to have the visit changed
	 * @param visitId To overwrite the id of the new visit from the previous one
	 * @return
	 */
	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/{visitId}/change")
	public String processNewVisitFormTEST(@Valid Visit visit, BindingResult result, Pet pet, @PathVariable("visitId") int visitId) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		}
		else {
			visit.setId(visitId);
			pet.addVisit(visit);
			this.visits.save(visit);
			return "redirect:/owners/{ownerId}";
		}
	}

}
