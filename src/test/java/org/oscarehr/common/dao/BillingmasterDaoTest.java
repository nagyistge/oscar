/**
 * Copyright (c) 2001-2002. Department of Family Medicine, McMaster University. All Rights Reserved.
 * This software is published under the GPL GNU General Public License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * This software was written for the
 * Department of Family Medicine
 * McMaster University
 * Hamilton
 * Ontario, Canada
 */
package org.oscarehr.common.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.oscarehr.common.dao.utils.EntityDataGenerator;
import org.oscarehr.common.dao.utils.SchemaUtils;
import org.oscarehr.util.SpringUtils;

import oscar.entities.Billingmaster;
import oscar.entities.WCB;
import oscar.oscarBilling.ca.bc.data.BillingmasterDAO;

public class BillingmasterDaoTest extends DaoTestFixtures {

	private BillingmasterDAO dao =  SpringUtils.getBean(BillingmasterDAO.class);

	@Before
	public void before() throws Exception {
		SchemaUtils.restoreTable("billingmaster", "wcb");
	}

	@Test
	public void testCreate() throws Exception {
		Billingmaster master = new Billingmaster();
		EntityDataGenerator.generateTestDataForModelClass(master);
		master.setBillingNo(99999);
		dao.save(master);

		int count = dao.updateBillingUnitForBillingNumber("NIHRENASEIBE", 99999);
		assertTrue(count == 1);
	}
	
	@Test
	public void testUpdateBillingUnitForBillingNumber() {
		Billingmaster b = new Billingmaster();
		b.setBillingUnit("AS");
		b.setBillingNo(999);
		dao.save(b);
		
		int i = dao.updateBillingUnitForBillingNumber("BU", 999);
		assertTrue(i == 1);
	}
	
	@Test
	public void testMarkListAsBilled() {
		Billingmaster b = new Billingmaster();
		b.setBillingUnit("AS");
		b.setBillingNo(999);
		dao.save(b);
		
		int i = dao.markListAsBilled(Arrays.asList(new String[] {String.valueOf(b.getBillingmasterNo())}));
		assertTrue(i == 1);
	}
	
	@Test
	public void testGetWcbByBillingNo() throws Exception {
		WCB wcb = new WCB();
		EntityDataGenerator.generateTestDataForModelClass(wcb);
		wcb.setBilling_no(999);
		dao.save(wcb);
		
		assertNotNull(dao.getWcbByBillingNo(999));
	}
}