/**
 * Created by tomson.ngassa on 6/17/14.
 */

'use strict';

xdescribe('BHCDS > Tools ans Resources', function() {
    var toolsandresourcesPath = 'toolsandresources';

    it('should route to tools and resources page', function() {
        browser.get('#/' + toolsandresourcesPath);
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl + toolsandresourcesPath);
    });
});

