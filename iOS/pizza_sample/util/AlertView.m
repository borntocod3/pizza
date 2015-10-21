//
// AlertView.m
//

#import "AlertView.h"

@implementation AlertView

// AlertView初期化
- (id)initWithTitle:(NSString *)title message:(NSString *)message completion:(void (^)(NSInteger buttonIndex))completion cancelButtonTitle:(NSString *)cancelButtonTitle otherButtonTitles:(NSString *)otherButtonTitles, ...
{
	self = [super initWithTitle:title message:message delegate:self cancelButtonTitle:cancelButtonTitle otherButtonTitles:nil];
	
	if (self != nil) {
		_completion = completion;
		
		va_list _arguments;
		va_start(_arguments, otherButtonTitles);
		
		for (NSString *key = otherButtonTitles; key != nil; key = (__bridge NSString *)va_arg(_arguments, void *)) {
			[self addButtonWithTitle:key];
		}
		
		va_end(_arguments);
	}
	
	return self;
}

#pragma mark - UIAlertView delegate method

// ボタンがクリックされた時に呼ばれるメソッド
- (void)alertView:(UIAlertView*)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
	if (_completion != nil) {
		_completion(buttonIndex);
	}
}

@end
