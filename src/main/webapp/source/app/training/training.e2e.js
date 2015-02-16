/**
 * Created by tomson.ngassa on 6/17/14.
 */

'use strict';


describe('BHCDS > Training', function() {
    var trainingPath = 'training';

    it('should route to training page', function() {
        browser.get('#/' + trainingPath);
        expect(browser.getCurrentUrl()).toEqual( browser.baseUrl + trainingPath);
    });

});
