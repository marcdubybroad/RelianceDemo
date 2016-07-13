#!/usr/bin/python

import sys

print 'Number of arguments:', len(sys.argv), 'arguments.'
print 'Argument List:', str(sys.argv)
print 'input file:', str(sys.argv[1])
outputFile = str(sys.argv[2])
print 'output file:', outputFile

with open('/Users/mduby/Apps/WorkspaceIntelliJ/dig-diabetes-portal/RelianceDemo/' + outputFile, 'w') as f:
    f.write("%s\n" % str('numCases=109'))
    f.write("%s\n" % str('numControls=86'))
    f.write("%s\n" % str('numCaseCarriers=1'))
    f.write("%s\n" % str('numControlCarriers=1'))
    f.write("%s\n" % str('numCaseVariants=1'))
    f.write("%s\n" % str('numControlVariants=1'))
    f.write("%s\n" % str('numInputVariants=10'))
    f.write("%s\n" % str('pValue=0.4699'))
    f.write("%s\n" % str('beta=-1.0748'))
    f.write("%s\n" % str('stdError=1.4872'))
    f.write("%s\n" % str('ciLevel=0.95'))
    f.write("%s\n" % str('ciLower=-3.989712'))
    f.write("%s\n" % str('ciUpper=1.8401120000000002'))

f.close()
