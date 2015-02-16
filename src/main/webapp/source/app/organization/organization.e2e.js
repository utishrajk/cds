/**
 * Created by tomson.ngassa on 6/17/14.
 */

'use strict';

describe('BHCDS > Organization', function() {
    var organizationPath ='organization';

    it('should route to organization page', function() {
        browser.get('#/' + organizationPath);
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl + organizationPath);
    });

});
